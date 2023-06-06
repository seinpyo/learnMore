package hellojpa;

import hellojpa.associate.AssociateMember;
import hellojpa.associate.Team;
import hellojpa.cascade.Child;
import hellojpa.cascade.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class LazyMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{

            Team team = new Team();
            team.setName("국내");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("국외");
            em.persist(team2);

            AssociateMember member1 = new AssociateMember();
            member1.setUsername("이정재");
            member1.setTeam(team2);
            em.persist(member1);

            AssociateMember member2 = new AssociateMember();
            member2.setUsername("정우성");
            member2.setTeam(team);
            em.persist(member2);
            
            em.flush();
            em.clear();
            
            //AssociateMember m = em.find(AssociateMember.class, member1.getId());

            //AssociateMember에 team은 지연로딩 설정되어있음(fetch = FetchType.LAZY)
            //System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());   // class hellojpa.associate.Team$HibernateProxy$ : 프록시로 가져옴

            System.out.println("CASCADE==============================================");

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

//            em.persist(child1);
//            em.persist(child2);
            em.persist(parent); //CASCADE 설정 => child 까지 같이 persist 해줌

            System.out.println("OrphanRemoval==============================================");

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            //  findParent.getChildList().remove(0);    //0번 child 지움
            // Parent 엔티티의 childList에서 삭제된 child를 CHILD 테이블에서 삭제 //delete from Child where id = ?

            em.remove(findParent);  //둘 다 지워짐

            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
