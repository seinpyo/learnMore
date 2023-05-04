package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
//애플리케이션 실제 동작에 필요한 구현 객체를 생성
//생성한 객체 인스턴스의 참조(래퍼런스)를 생성자를 통해 주입
    //실제 실행 시점(런타임)에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달하여 클라이언트와 서버의 실제 의존관계가 연결되는 것
    //= 의존관계주입 DI
    //DI를 해주는 AppConfig같은 클래스를 DI컨테이너(=IoC컨테이너, 어샘블러, 오브젝트 팩토리) 라고 부름

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository()); //생성자 주입
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    //@Bean 으로 스프링 컨테이너에 등록
}
