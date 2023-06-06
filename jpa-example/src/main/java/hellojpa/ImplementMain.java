package hellojpa;

import hellojpa.implement.Item;
import hellojpa.implement.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ImplementMain {
    public static void main1(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();   //쓰레드 간에 공유 XXXXXXX

        EntityTransaction tx = em.getTransaction(); //JPA의 모든 데이터 변경은 트랜젝션 안에서 실행
        tx.begin();
        try{
            Movie movie = new Movie();
            movie.setDirector("김감독");
            movie.setActor("이배우");
            movie.setName("영화제목");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Item findItem = em.find(Item.class, movie.getId());
            System.out.println("findItem = " + findItem);
            //TABLE_PER_CLASS 전략에서 부모 클래스를 조회하려고하면 서브타입을 모두 union 하는 복잡한 쿼리가 발생한다

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);
            //join 전략: movie 테이블과 item 테이블을 조인해서 movie 객체를 조회한다.
            //single-table 전략: join 없이 그냥 조회 (성능상의 이점)


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
