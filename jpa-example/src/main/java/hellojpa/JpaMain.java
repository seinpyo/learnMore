package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();   //쓰레드 간에 공유 XXXXXXX

        EntityTransaction tx = em.getTransaction(); //JPA의 모든 데이터 변경은 트랜젝션 안에서 실행
        tx.begin();

        try {
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("helloUser");    //수정
            List<Member> result = em.createQuery("select m from Member as m ", Member.class) //JPQL => 객체지향 SQL
                    .setFirstResult(0)  //5번부터
                    .setMaxResults(8)   //8번까지 가져오는 paging
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.getName() = " + member.getName());
            }

            Member member1 = em.find(Member.class, 1L);
            // 위에서 전체 조회를 했기 때문에 다시 조회하지 않고 1차캐시에서 값을 찾아옴 ( = select 쿼리 실행 x )

            em.clear();
            // 영속성 컨텍스트 비우기

            Member member2 = em.find(Member.class, 1L);
            // 영속성 컨텍스트에 아무것도 없으니 다시 조회해야함 ( = select 쿼리 실행 o )

            em.close();
            //영속성 컨텍스트 종료

            Member member3 = em.find(Member.class, 1L);


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
