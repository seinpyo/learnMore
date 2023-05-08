package scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {
    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");  //find prototypeBean1 다음 라인에 PrototypeBean.init 출력
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");  //find prototypeBean2 다음 라인에 PrototypeBean.init 출력
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        //프로토타입 스코프의 빈은 스프링 컨테이너에서 빈을 조회할 때 생성, 초기화 메서드도 실행된다

        System.out.println("prototypeBean1 = " + prototypeBean1);   //prototypeBean1 = scope.PrototypeTest$PrototypeBean@1fc32e4f
        System.out.println("prototypeBean2 = " + prototypeBean2);   //prototypeBean2 = scope.PrototypeTest$PrototypeBean@2f67b837
        //요청 때마다 새로 생성되므로 prototypeBean1과 prototypeBean2가 서로 다른 인스턴스임

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close(); //호출 안됨
        //스프링 컨테이너가 생성과 의존관계 주입, 초기화까지만 관여하고 그 이후 관리하지 않으므로
        //프로토타입 빈을 조회한 클라이언트가 알아서 관리 해야 함!! @PreDestroy 같은 종료 메서드가 전혀 실행되지 않는다.
        //↓ 필요 시 직접 호출
        prototypeBean1.close(); //PrototypeBean.close
        prototypeBean2.close(); //PrototypeBean.close
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("PrototypeBean.close");
        }
    }
}
