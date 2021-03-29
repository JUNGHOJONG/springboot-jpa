package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager entityManager;

    // 생성
    public void save(Item item) {
        if (item.getId() == null) {
            entityManager.persist(item);
            return;
        }
        entityManager.merge(item);
    }

    // 단건 조회
    public Item findOne(Long itemId) {
        return entityManager.find(Item.class, itemId);
    }

    // 모두 조회
    public List<Item> findAll() {
        return entityManager.createQuery("select i from Item i", Item.class).getResultList();
    }

}
