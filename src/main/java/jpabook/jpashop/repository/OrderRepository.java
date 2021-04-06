package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager entityManager;

    public Long save(Order order) {
        entityManager.persist(order);
        return order.getId();
    }

    public Order findOne(Long orderId) {
        return entityManager.find(Order.class, orderId);
    }

    /**
     * orderSearch가 null값이 아닐 때
     */
    public List<Order> findAll(OrderSearch orderSearch) {
        // 주문과 회원 조인(orderSearch가 null값이 아닐 때)
        return entityManager.createQuery("select o from Order o join o.member m" +
                " where o.status = : status" +
                " and m.name = : name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .getResultList();
    }

    /**
     * orderSearch가 null값 가능 시(1)
     * JPQL로 처리
     */
    public List<Order> findAllByJpql(OrderSearch orderSearch) {
        // 주문과 회원 조인(orderSearch가 null값이 아닐 때)
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = : status";
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.name = : name";
        }

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class)
                                            .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            typedQuery.setParameter("status", orderSearch.getOrderStatus());
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            typedQuery.setParameter("name", orderSearch.getMemberName());
        }

        return typedQuery.getResultList();
    }

    /**
     * orderSearch가 null값 가능 시(2)
     * JPQL Criteria로 처리
     */
    public List<Order> findAllByJpqlCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Join<Object, Object> member = orderRoot.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(orderRoot.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(member.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        criteriaQuery.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery).setMaxResults(1000); //최대

        return query.getResultList();
    }

    /**
     * query dsl(미완성)
     */
    public List<Order> findAllByQueryDsl(OrderSearch orderSearch) {
        return null;
    }

}

