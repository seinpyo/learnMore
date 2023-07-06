package jpql;

import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;
import jpql.dto.MemberDto;

import javax.persistence.*;
import java.util.List;

public class JpqlMain3 {
    public static void main3(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setUsername(null);
            member2.setAge(60);
            member2.setType(MemberType.USER);

            Member member3 = new Member();
            member3.setUsername("사용자");
            member3.setAge(30);
            member3.setType(MemberType.USER);

            member.setTeam(team);
            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();


            String query = "select " +
                    "case when m.age <= 10 then '학생' " +
                    "when m.age >= 60 then '노인' " +
                    "else '일반 요금' " +
                    "end " +
                    "from Member m";


            String query2 = "select coalesce(m.username, '이름 없는 회원') from Member m ";

            String query3 = "select locate('de', 'abcdefg') from Member m";

            String query4 = "select function('group_concat', m.username) from Member m";

            List<String> result = em.createQuery(query4)
                    .getResultList();

            for(String s : result) {
                System.out.println("s = " + s);
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
