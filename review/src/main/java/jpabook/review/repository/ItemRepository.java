package jpabook.review.repository;

import jpabook.review.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null) {
            em.persist(item);   //완전 신규 등록 시
        } else {
            em.merge(item);     //병합: 준영속상태의 엔티티를 영속 상태로 변경 -> 모든 속성이 다 변경됨 (원하는 필드 값만 수정 불가)
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
