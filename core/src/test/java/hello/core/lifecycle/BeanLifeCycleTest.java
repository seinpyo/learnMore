package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);  
            //ApplicationContext가 상속받는 인터페이스로 close(); 사용 가능
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        @Bean //NetworkClient에서 생성한 메서드를 초기화, 소멸 메서드로 지정
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }

    //destroyMethod 의 디폴트 값 = "(inferred)"  /(추론)
    //  -> "close" 나 "shutdown" 이라는 이름의 메서드를 자동으로 호출해준다
    // 디폴트로 냅둬도(명시하지 않아도) 잘 작동!
    // 추론 기능을 쓰기 싫을 때는 destroyMethod = "" 로 냅두기


}
