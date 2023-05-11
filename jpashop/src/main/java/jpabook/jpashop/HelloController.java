package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        //Model 을 통해 컨트롤러에서 뷰로 데이터를 넘긴다
        model.addAttribute("data", "hello!");
        return "hello";
    }
}
