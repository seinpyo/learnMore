package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {
    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository = " + memberRepository); //memberRepository = hello.core.member.MemoryMemberRepository@53f0a4cb
        System.out.println("memberService -> memberRepository1 = " + memberRepository1); //memberService -> memberRepository1 = hello.core.member.MemoryMemberRepository@5cdd09b1
        System.out.println("orderService -> memberRepository2 = " + memberRepository2); //orderService -> memberRepository2 = hello.core.member.MemoryMemberRepository@5cdd09b1

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

        //new 에 의해 생성 되었는데도 같은 인스턴스가 공유되고 있음
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());    //bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$ccc7ff19

        //보통클래스 패스가 아니라 SpringCGLIB을 포함해 복잡하게 생김
        // => 스프링이 CGLIB 이라는 바이트코드 조작 라이브러리를 사용 -> AppConfig 를 상속받은 임이의 다른 클래스를 만들고
        // 만들어진 클래스를 스프링 빈으로 등록한 것
        // 이 임의의 클래스가 싱글톤이 보장되도록 해줌
        //AppConfig$$EnhancerBySpringCGLIB$$ccc7ff19 는 AppConfig의 자식이므로 AppConfig.class를 조회했을 때 나옴
    }
}
