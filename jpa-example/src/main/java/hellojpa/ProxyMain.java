package hellojpa;

import hellojpa.associate.AssociateMember;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ProxyMain {
    public static void main4(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            AssociateMember member = new AssociateMember();
            member.setUsername("김선생");
            em.persist(member);

            AssociateMember member2 = new AssociateMember();
            member2.setUsername("최선생");
            em.persist(member2);

            em.flush();
            em.clear();

            AssociateMember findMember = em.getReference(AssociateMember.class, member.getId()); //쿼리 호출 x
            System.out.println("findMember.getClass() = " + findMember.getClass()); //하이버네이트가 만든 가짜 객체
            System.out.println("-------------------------------------------");
            System.out.println("findMember.getUsername() = " + findMember.getUsername());   //실제 값이 사용될 때 쿼리 호출

            AssociateMember findMember2 = em.find(AssociateMember.class, member2.getId());  //실제 엔티티를 가져오는 find()
            System.out.println("findMember2.getClass() = " + findMember2.getClass());

            logic(findMember, findMember2);

            AssociateMember reference = em.getReference(AssociateMember.class, member2.getId());  //이미 영속성컨텍스트에 있는 엔티티를 getReference()함
            System.out.println("(reference == findMember2) = " + (reference == findMember2));   //true
            // 굳이 프록시 만들지 않고 영속성 컨텍스트에서 실제 객체 가져옴

            //---------------------------------------------------------------------------------------------
            em.flush();
            em.clear();

            AssociateMember mb = new AssociateMember();
            member.setUsername("박선생");
            em.persist(mb);

            AssociateMember refMember = em.getReference(AssociateMember.class, mb.getId());   //프록시 생성
            AssociateMember associateMember = em.find(AssociateMember.class, mb.getId());   //쿼리 실행, DB 조회

            System.out.println("(refMember == findMember) = " + (refMember == associateMember));   //true
            //refMember와 associateMember의 getClass의 결과는 class hellojpa.associate.AssociateMember$HibernateProxy$... 로 나옴

            em.flush();
            em.clear();

            //빈 영속성 콘텍스트에 프록시 객체 생성
            AssociateMember detachMember = em.getReference(AssociateMember.class, mb.getId());

            //후 준영속 상태로 만들음
            em.detach(detachMember);    //혹은 em.close() 나 em.clear() 도 같은 결과 나옴

            //준영속 상태의 프록시를 초기화 시도 (영속성 컨텍스트를 통해 DB를 조회해야하는데 도움을 받을 수 없음)
            //detachMember.getUsername(); //org.hibernate.LazyInitializationException: could not initialize proxy [hellojpa.associate.AssociateMember#3] - no Session

            //---------------------------------------------------------------------------------------

            AssociateMember referenceMember = em.getReference(AssociateMember.class, mb.getId());
            System.out.println("referenceMember = " + referenceMember.getClass().getName());
            System.out.println("Before: isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(referenceMember));

            Hibernate.initialize(referenceMember); //강제초기화
            System.out.println("After: isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(referenceMember));


            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void logic(AssociateMember m1, AssociateMember m2) {
        System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));  //m1 proxy, m2 실제 엔티티로 == 연산 결과가 false로 나온다
    }
}
