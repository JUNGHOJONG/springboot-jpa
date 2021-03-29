package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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

//    public List<Order> findAll() {
//        return entityManager.createQuery("select o from Order o", Order.class).getResultList();
//    }
}

