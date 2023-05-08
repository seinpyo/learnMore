package scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);

        //매번 새로 만들어진 걸 받고 싶어서 프로토타입 스코프의 빈을 사용했는데
        //제작 의도와 벗어난 결과가 나온다
    }

    //만약 ClientBean2 라는 싱글톤 빈도 PrototypeBean를 주입 받는다면
    //ClientBean2도 PrototypeBean를 호출했으므로 이때는 새로 생성됨
    // = ClientBean과 ClientBean2는 서로 다른 PrototypeBean 인스턴스를 가지고 있게 될 것
    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean;  //생성 시점에 주입됨 (앞으로 지금 생성된 빈을 불러오게 됨)

        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }


    @Scope("prototype")
    static class PrototypeBean{
        private  int count = 0;
        public void addCount() { count++; }
        public int getCount() { return count; }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init : " + this);
        }

        @PreDestroy
        public void destroy() { //실행 안될 예정
            System.out.println("PrototypeBean.destroy" );
        }
    }
}
