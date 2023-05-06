package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService state1 = ac.getBean(StatefulService.class);
        StatefulService state2 = ac.getBean(StatefulService.class);

        //ThreadA: user가 10000원 주문
        //state1.order("user", 10000);
        //ThreadB: member가 20000원 주문
//        state1.order("member", 20000);
        //ThreadA: user가 주문 금액 조회
//        int price = state1.getPrice();
//        System.out.println("price = " + price);
//        Assertions.assertThat(state1.getPrice()).isEqualTo(20000);

        //user가 주문 하고 주문 금액을 조회하기전에 member가 주문 -> price라는 필드가 공유되고 있으므로
        //member가 주문한 20000이 price의 값으로 변경되었음
        //user의 주문금액 조회 결과가 20000으로 출력 됨

        //이하 price를 필드 대신 파라미터로 변경
        int userPrice = state1.order("user", 10000);
        int member = state1.order("member", 20000);

        System.out.println("userPrice = " + userPrice);

        //스프링 빈은 항상 무상태(stateless)로 설계할 것
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}