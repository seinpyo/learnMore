package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main1(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();   //쓰레드 간에 공유 XXXXXXX

        EntityTransaction tx = em.getTransaction(); //JPA의 모든 데이터 변경은 트랜젝션 안에서 실행
        tx.begin();

        try {
            MemberSequence member = new MemberSequence();
            member.setUsername("A");
            MemberSequence member2 = new MemberSequence();
            member2.setUsername("B");
            MemberSequence member3 = new MemberSequence();
            member3.setUsername("C");

            System.out.println("=====================================");

            em.persist(member); //시퀀스 다음 값을 AllocationSize값 만큼 다음 시퀀스 값을 가져와 메모리에 올림 ( 1 ~ 50 )
            //call next value for MEMBER_SEQ = 51 (50개를 가져왔으므로 시퀀스 다음 값은 51)

            em.persist(member2);    //메모리에서 다음 값 가져옴
            em.persist(member3);    //메모리에서 다음 값 가져옴

            System.out.println("member = " + member.getId());   //1
            System.out.println("member2 = " + member2.getId()); //2
            System.out.println("member3 = " + member3.getId()); //3

            //이제 다음번 시퀀스는 51 부터 시작
            //똑같이 실행 시 id 값이 51,52,53 이 됨
            //한번에 50개씩 들고 오니 동시성 문제도 해결 할 수 있음

            System.out.println("=====================================");

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
