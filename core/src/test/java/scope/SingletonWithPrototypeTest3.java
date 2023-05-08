package scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest3 {
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
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {
        private final Provider<PrototypeBean> prototypeBeansProvider;
        //build.gradle dependencies 에 implementation 'javax.inject:javax.inject:1' 추가 필요
        //자바 표준이므로 스프링 의존 x
        //딱 DL 기능 (get()) 만 제공

        ClientBean(Provider<PrototypeBean> prototypeBeansProvider) {
            this.prototypeBeansProvider = prototypeBeansProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeansProvider.get();
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
        public void destroy() {
            System.out.println("PrototypeBean.destroy" );
        }
    }
}

//프로토타입 빈은 언제 사용 ? = 매번 사용할 때 마다 의존관계 주입이 완료된 새로운 객체가 필요할 때
//'ObjectProvider', 'JSR-330 Provider' 는 프로토타입 스코프 빈을 찾을 때 말고도 DL이 필요할 때 언제든 사용 가능
// -> 순환참조가 일어나는 경우, 지연 참조 할 때 등
//'ObjectProvider' 은 DL을 위한 편의 기능을 많이 제공해주고 별도 의존관계 추가가 필요없어서 편리하다!
// 스프링이 아닌 다른 컨테이너에서도 사용할 일이 있다면 'JSR-330 Provider' 을 사용해야 한다

