package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hell(Model model) {
        model.addAttribute("data", "spring!");
        return "hello";
        //컨트롤러에서 리턴 값으로 문자를 반환하면 뷰리졸버(veiwResolver)가 화면을 찾아 처리
        //resources > templates의 hello.html을 return
    }
}
