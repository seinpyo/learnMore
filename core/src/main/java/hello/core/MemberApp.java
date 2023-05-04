package hello.core;

import hello.core.member.Grade;
import hello.core.member.MemberService;
import hello.core.member.Member;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //↑ applicationContext를 스프링 컨테이너라고 함(인터페이스임)
        // AppConfig에 있는 설정정보들(@Bean으로 등록 된 메서드를 모두 실행하고 그 결과로 반환된 객체들)을 스프링 컨테이너에 넣고 관리 해줌
        // XML 기반 혹은 애노테이션 기반으로 자바 설정 클래스를 만들 수 있음

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        //@Bean의 이름이 "memberService", 타입이 MemberService 인 빈을 찾기
        //※ Bean의 이름은 겹치게 설정하지 않도록 한다

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new Member = " + member.getName());
        System.out.println("fin dMember = " + findMember.getName());
    }
}
