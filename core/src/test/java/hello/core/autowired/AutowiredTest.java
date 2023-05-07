package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean{
        @Autowired(required = false) //required = true 로 하면 NoSuchBeanDefinitionException
        public void setNoBean1(Member noBean1) {
            //Member는 스프링이 관리하는 빈이 아니므로 스프링 컨테이너에는 Member 인스턴스가 없음
            System.out.println("noBean1 = " + noBean1); //자동 주입 대상이 없으면 메서드가 호출되지 않으므로 이 코드는 실행되지 x
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("setNoBean2 = " + noBean2);  //setNoBean2 = null
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("setNoBean3 = " + noBean3);  //setNoBean3 = Optional.empty
        }
    }
}
