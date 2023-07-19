package jpabook.review.repository;

import jpabook.review.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@Autowired    // -> spring boot 에서는 @PersistenceContext 대신 Autowired 사용 가능
    private final EntityManager em;

//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }         --> @RequiredArgsConstructor 얘가 대신 작성해줌 + 생성자가 1개만 있을 경우 Autowired 생략 가능

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
