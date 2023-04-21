package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//@Controller 어노테이션이 붙은 객체는
//스프링이 로드될 때 객체를 생성해서 스프링 컨테이너에서 관리 = 빈이 관리된다
@Controller
public class MemberController {
    private final MemberService memberService;

    //@Autowired가 붙은 생성자 호출 시 스프링 컨테이너에 있는 MemberService와 연결 시켜줌
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


}
