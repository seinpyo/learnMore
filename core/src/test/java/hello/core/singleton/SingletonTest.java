package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        //new SingletonService(); -> 불가능
        SingletonService singletonService1 = SingletonService.getInstance(); //singletonService1 = hello.core.singleton.SingletonService@55fe41ea
        SingletonService singletonService2 = SingletonService.getInstance(); //singletonService2 = hello.core.singleton.SingletonService@55fe41ea

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        //*test 시 결과를 프린트로 확인하지말고 Assertions 등을 활용하여 자동화 할 것*

        assertThat(singletonService1).isSameAs(singletonService2);

        //**싱글톤 패턴 문제점
        //1. 싱글톤 패턴을 구현하는 코드 자체가 많이 들어감 (SingletonService)
        //2. 의존관계 상 클라이언트가 구체 클래스에 의존(객체가 필요할 때 구체클래스.getInstance()가 필요) -> DIP 위반
        //3. 클라이언트가 구체 클래스에 의존해서 OCP를 위반할 가능성이 높다
        //4. 테스트 어려움
        //5. 내부 속성을 변경하거나 초기화 하기 어렵다.
        //6. private 생성자로 자식 클래스를 만들기 어렵다
        //결론 : 유연성이 떨어진다

        //스프링 컨테이너는 이런 싱글톤 패턴의 문제점을 해결하면서도 객체 인스턴으를 싱글톤으로 관리한다
        //스프링 빈 == 싱글톤으로 관리 되는 빈 !
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class); //singletonService1 = hello.core.member.MemberServiceImpl@77a7cf58
        MemberService memberService2 = ac.getBean("memberService", MemberService.class); //singletonService2 = hello.core.member.MemberServiceImpl@77a7cf58
        //스프링 컨테이너가 Appconfig에서 생성된 스프링 빈을 반환해줌

        System.out.println("singletonService1 = " + memberService1);
        System.out.println("singletonService2 = " + memberService2);
        //*test 시 결과를 프린트로 확인하지말고 Assertions 등을 활용하여 자동화 할 것*

        assertThat(memberService1).isSameAs(memberService2);
    }

}
