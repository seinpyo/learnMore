package jpql;

import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlMain4 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setUsername(null);
            member2.setAge(60);
            member.setTeam(team);
            member2.setType(MemberType.USER);

            Member member3 = new Member();
            member3.setUsername("사용자");
            member3.setAge(30);
            member.setTeam(team2);
            member3.setType(MemberType.USER);

            member.setTeam(team);
            member.setTeam(team2);
            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            System.out.println("==============================================");

            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "사용자")
                    .getResultList();

            System.out.println("resultList = " + resultList);
            System.out.println("==============================================");

//            em.flush();
//            em.clear();

            //FLUSH 자동 호출( commit 하거나, query 나갈 때 )
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();   //벌크연산

            System.out.println("result count ="  + resultCount);

            System.out.println("member.getAge() = " + member.getAge());     //sout 결과: 10 / db 결과: 20
            System.out.println("member2.getAge() = " + member2.getAge());   //sout 결과: 60 / db 결과: 20
            System.out.println("member3.getAge() = " + member3.getAge());   //sout 결과: 30 / db 결과: 20

            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge()); //20



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
