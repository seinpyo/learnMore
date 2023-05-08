package scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest2 {
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
        private final ObjectProvider<PrototypeBean> prototypeBeansProvider;
        //ObjectFactory 는 getObject() (Dependency Lookup 지원) 만 제공
        //ObjectProvider 는 getObject() 외에 상속, 옵션, 스트림 처리 등 편의 기능이 더 있다.
        //둘 다 별도의 라이브러리가 필요없지만 스프링에 의존적이다.

        ClientBean(ObjectProvider<PrototypeBean> prototypeBeansProvider) {
            this.prototypeBeansProvider = prototypeBeansProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeansProvider.getObject();   //스프링 컨테이너를 통해 해당 빈(PrototypeBean)을 찾아서 반환한다
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
