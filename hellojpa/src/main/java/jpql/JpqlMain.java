package jpql;

import jpql.domain.Member;
import jpql.dto.MemberDto;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            for(int i=0; i<100; i++) {
                Member member = new Member();
                member.setUsername(i==0? "김춘배" : "member" + i);
                member.setAge(i);
                em.persist(member);
            }

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);  //반환 타입 명확

            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "김춘배")
                    .getSingleResult();//결과가 명확히 1개 일 떄
            System.out.println("singleResult = " + singleResult.getUsername());

            em.flush(); em.clear();

            //DTO를 통해 여러값 가지고 오기
            //패키지 명을 포함하는 전체 클래스명 입력, 순서와 타입이 일치하는 생성자가 필요하다
            List<MemberDto> result = em.createQuery("select new jpql.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            MemberDto memberDto = result.get(0);
            System.out.println("memberDto.getUsername() = " + memberDto.getUsername());
            System.out.println("memberDto.getAge() = " + memberDto.getAge());

            em.flush(); em.clear();

            //페이징

            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("resultList = " + resultList.size());
            for (Member item : resultList) {
                System.out.println("item = " + item);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
