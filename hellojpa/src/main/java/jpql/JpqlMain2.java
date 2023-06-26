package jpql;

import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;

public class JpqlMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from Member m, Team t where m.username = t.name";
//            List<Member> result = em.createQuery(query, Member.class)
//                            .getResultList();


            String query2 = "select m from Member m left join m.team t on t.name = 'teamA'";
//            List<Member> result2 = em.createQuery(query2, Member.class)
//                    .getResultList();
            //left outer join Team team1_
            //     on member0_.TEAM_ID=team1_.id
            //     and ( team1_.name='teamA' )


            //타입
            String query3 = "select m.username, 'HELLO', true from Member m left join m.team t on t.name = 'teamA'" +
                            "where m.type = jpql.domain.MemberType.ADMIN";
            //enum 클래스 사용 시 패키지 이름까지 전부 적기
            List<Object[]> result3 = em.createQuery(query3)
                    .getResultList();

            String query4 =  "select m.username, 'HELLO', true from Member m " +
                    "where m.type = :userType";
            List<Object[]> result4 = em.createQuery(query4)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for(Object[] objects : result4) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
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
