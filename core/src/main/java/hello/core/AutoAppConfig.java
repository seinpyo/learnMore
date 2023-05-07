package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //@Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
        basePackages = "hello.core", // hello.core 의 하위 패키지만 scan 의 대상이 됨
            // -> 설정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 됨 (hello.core 하위 클래스를 모두 검색)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
