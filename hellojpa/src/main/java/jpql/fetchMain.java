package jpql;

import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class fetchMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team1 = new Team();
            team1.setName("teamA");

            Team team2 = new Team();
            team2.setName("teamB");

            em.persist(team1);
            em.persist(team2);

            Member member = new Member();
            member.setUsername("회원1");
            member.setTeam(team1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(team1);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(team2);

            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();


            String query = "select m from Member m";

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            for(Member item : result) {
                    //System.out.println("member = " + item.getUsername() + ", " + item.getTeam().getName());
                //지연로딩으로 설정 되어있으므로 team은 proxy로 존재하다가 .getName()이 실행될 때 쿼리가 나간다 (조건에 해당하는 팀의 갯수만큼 나감)
                //회원1, 팀A(sql 실행 -> 캐시에 적재)
                //회원2, 팀A(1차캐시에서 가져옴)
                //회원3, 팀B(sql 실행 -> 캐시에 적재)     //n+1 문제
            }

            String queryFetch = "select m from Member m join fetch m.team";

            List<Member> queryFetchResult = em.createQuery(queryFetch, Member.class)
                    .getResultList();
                /* select
                        member0_.id as id1_0_0_,
                        team1_.id as id1_3_1_,
                        member0_.age as age2_0_0_,
                        member0_.TEAM_ID as team_id5_0_0_,
                        member0_.type as type3_0_0_,
                        member0_.username as username4_0_0_,
                        team1_.name as name2_3_1_
                    from
                        Member member0_
                    inner join
                        Team team1_
                    on member0_.TEAM_ID=team1_.id */

            for(Member fetchItem : queryFetchResult) {
                System.out.println("member = " + fetchItem.getUsername() + ", " + fetchItem.getTeam().getName());
            }

            String queryForTeam = "select t From Team t join fetch t.members";
            // "select t From Team t" -> 결과는 2개인데
            // "select t From Team t join fetch t.members" -> 결과는 3개임 : member 가 3명이라 low가 3줄 나와서 (일대다 관계일 때!! 다대일은 별 상관 없음)

            String queryDistinct = "select distinct t From Team t join fetch t.members";
            // sql에서 사용하는 distinct는 모든 데이터가 일치해야 중복을 제거해줌
            // {teamId: 1, teamName: A, MemberId: 1, MemberName: kim}, {teamId: 1, teamName: A, MemberId: 2, MemberName: lee} -> 제거 안됨 !!
            // jpql의 distinct는 같은 식별자를 가진 Team 엔티티를 제거

            List<Team>  queryDistinctResult = em.createQuery(queryDistinct, Team.class)
                            .getResultList();

            for(Team team : queryDistinctResult) {
                System.out.println("team = " + team.getName() + " | members = ");
                for(Member teamMember : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }
            //결과
            //  team = teamA | members =
            //  -> member = Member{id=3, username='회원1', age=0}
            //  -> member = Member{id=3, username='회원1', age=0}
            //  team = teamB | members =
            //  -> member = Member{id=3, username='회원1', age=0}





            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
