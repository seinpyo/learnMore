package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JPQLMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{

            List<Member> result = em.createQuery(
                    "select m From Member m where m.username like '%김%'", Member.class
                ).getResultList();//Member 엔티티 대상으로 쿼리

            for(Member member : result) {
                System.out.println("member = " + member);
            }

            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
