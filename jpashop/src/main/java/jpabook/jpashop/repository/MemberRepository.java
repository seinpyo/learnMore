package jpabook.jpashop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext
    private final EntityManager em; //스프링 부트에서는 @PersistenceContext을 @Autowired로 대체할 수 있음 = 생성자 주입이 가능 함

    public void save(Member member) {
        em.persist(member);
    }

    //단건조회 em.find( 반환타입, pk );
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //jpql 쿼리의 대상은 테이블이 아닌 엔티티
    public List<Member> findAll() {
        //em.createQuery( "쿼리", 반환 타입 );
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
