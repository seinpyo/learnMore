package hellojpa;

import hellojpa.associate.AssociateMember;
import hellojpa.associate.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class AssociateMain {
    public static void main2(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            AssociateMember member = new AssociateMember();
            member.setUsername("member1");
            member.changeTeam(team);    //setTeam()을 연관 관계 편의 메서드로 수정
            em.persist(member);

            //Team은 연관관계의 주인이 아니라 getMembers에 굳이 add 안해도
            //flush 후 정상적으로 members를 조회할 수 있지만 순수 객체 상태를 고려해 양쪽에 값을 설정하는 것이 좋다
            //team.getMembers().add(member);
            //  -> 이 코드를 changeTeam()안에 넣어서 member.changeTeam(team); 실행 시 team의 members에도 값을 넣어주도록 하면 좋다~
            // -> 연관 관계 편의 메서드는 일대다에서 일에 넣어도 되고 다에 넣어도 됨

            AssociateMember findMember = em.find(AssociateMember.class, member.getId());
            List<AssociateMember> members = findMember.getTeam().getMembers();

            for(AssociateMember member1 : members) {
                System.out.println("m = " + member.getUsername());
            }

//            AssociateMember findMember = em.find(AssociateMember.class, member.getId());
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId);
            //연관 관계 설정이 없을 때는 팀 아이디를 찾으려면 좀 복잡한 과정을 거쳐야함

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

// 양방향 매핑시에 무한 루프에 주의!
// toString(), lombok, JSON 생성 라이브러리 등
// ex) member.toString() -> member 안에 team 이 있음 -> team.toString() 호출 -> team 안에 members -> members List 루프 돌면서
//  AssociateMember 객체 toString() 호출 -> 또 AssociateMember 객체 안에 Team 객체 있음 -> Team.toString() 호출 ... -> stack overflow
// * lombok 으로 toString 자동생성 하지말기 ,,,
// * controller 에서 Entity를 반환 하지말기... (DTO로 변환해서 반환하도록 한다)

//단방향 매핑만 해도 연관관계 매핑은 완료됨
//양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
//단방향 매핑을 잘하고 양방향은 필요할 때 추가해도 됨
//(JPQL 에서 역방향 탐색할 일이 많다)

