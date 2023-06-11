package hellojpa;

import hellojpa.type.Address;
import hellojpa.type.AddressEntity;
import hellojpa.type.Member;
import hellojpa.type.Period;
import org.hibernate.type.descriptor.java.LocalDateJavaDescriptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TypeMain {
    public static void main5(String[] args) {
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

            //값 컬렉션
            Member member1 = new Member();
            member1.setUsername("김유정");
            member1.setHomeAddress(new Address("사는시", "사는로", "23456"));

            member1.getFavoriteFoods().add("햄버거");
            member1.getFavoriteFoods().add("돈까스");
            member1.getFavoriteFoods().add("떡볶이");

            member1.getAddressHistory().add(new AddressEntity("살던시", "살던로", "34525"));
            member1.getAddressHistory().add(new AddressEntity("전에살던시", "전에살던로", "34123"));

            em.persist(member1);    //member1만 persist 하면 favorite_food, address 에도 함께 insert 됨 //생명주기를 member 에 의존

            System.out.println("==============================================================");

            em.flush(); em.clear();

            Member findMember = em.find(Member.class, member1.getId());     //select문 1(type_member 테이블 조회)

//            List<Address> addressHistory = findMember.getAddressHistory();  //select문 2(address 테이블 조회) //지연로딩
            List<AddressEntity> addressHistory = findMember.getAddressHistory();

            for(AddressEntity item : addressHistory) {
                System.out.println("addressHistory item = " + item);
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();  //select문 3(favorite_foods 테이블 조회) //지연로딩
            for(String food : favoriteFoods) {
                System.out.println("favoriteFoods food = " + food);
            }

            //값 수정
//            findMember.getHomeAddress().setCity("123");   //이런식으로 하면 안됨 (사이드 이펙트 발생 위험)
            Member alterTest1 = em.find(Member.class, 1L);
            Member alterTest2 = em.find(Member.class, 2L);

            Address newAdd = alterTest1.getHomeAddress();
//            alterTest1.setHomeAddress(new AddressEntity("바꿨시", newAdd.getStreet(), newAdd.getZipcode())); //일부만 변경
//            alterTest2.setHomeAddress(new AddressEntity("이사갔시", "이사갔로", "63451"));    //통으로 변경

            alterTest2.getFavoriteFoods().remove("햄버거");    //"햄버거" 제거
            alterTest2.getFavoriteFoods().add("치킨");    //"치킨" 추가

//equals 비교 후 제거 되기 때문에 equals 를 제대로 override 해놨어야 함
//            alterTest2.getAddressHistory().remove(new Address("전에살던시", "전에살던로", "34123"));
//            alterTest2.getAddressHistory().add(new Address("이전시", "이전로", "45341"));     //역시 통으로 변경



            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
