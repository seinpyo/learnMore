package hellojpa;

import hellojpa.type.Address;
import hellojpa.type.Member;
import hellojpa.type.Period;
import org.hibernate.type.descriptor.java.LocalDateJavaDescriptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class TypeMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Member member = new Member();
            member.setUsername("김현아");
            member.setHomeAddress(new Address("고양시", "충장로", "12345"));
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now().plusDays(10)));

            em.persist(member);

            //불변 객체를 변경하기
            Address address = new Address("고양시", "용현로", "54321");    //통으로 변경
            member.setHomeAddress(address);

            //값 타입 비교: equals() 메서드 재정의
            Address newAddress = new Address("고양시", "용현로", "54321");
            System.out.println("address.equals(newAddress) = " + address.equals(newAddress));   //address.equals(newAddress) = true

            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
