package hello.core.autowired.allbean;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {
    @Test
    void findAllBean(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 20000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);  // FixDiscountPolicy의 discount 실행 결과

        int rateDiscountPolicy = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPolicy).isEqualTo(2000); // rateDiscountPolicy의 discount 실행 결과
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
                //policyMap = {fixDiscountPolicy=hello.core.discount.FixDiscountPolicy@1c3146bc, rateDiscountPolicy=hello.core.discount.RateDiscountPolicy@56c698e3}
            System.out.println("policyList = " + policyList);
                //policyList = [hello.core.discount.FixDiscountPolicy@1c3146bc, hello.core.discount.RateDiscountPolicy@56c698e3]
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}
