package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//@Scope(value = "request")

//프록시 방식 : 클래스에 적용 시 TARGET_CLASS, 인터페이스라면 INTERFACES
//HTTP request 와 상관없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 줄 수 있다. (CGLIB)
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
    private String uuid;
    private String requestURL;
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " +
                message);
    }
    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();     //요청 때마다 다른 아이디를 부여 받음
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }
    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}
//request 가 오면 진짜 빈을 요청하는 위임 로직이 들어 있다.
//클라이언트가 myLogger.logic()을 호출할 때 가짜 프록시 객체의 메서드를 호출한 것임
//이 가짜 프록시 객체가 request 스코프으 ㅣ진짜 myLogger.logic()을 호출한다.
//가짜 프록시 객체는 원본 클래스를 상속받아 만들어졌기 때문에 클라이언트가 원본인지 가짜인지 모르게 사용가능(다형성)

//Provider 를 사용하든, 프록시를 사용하든 진짜 객체 조회를 꼭 필요한 시점까지 지연 처리 할 수 있다.
//프록시 객체를 사용하면 싱글톤 빈을 사용하듯 편리하게 request scope 를 사용할 수 있다는 장점

//싱글톤과 동일하게 작동하는 거 같아도 다르기 때문에 필요한 경우에만 사용한다!!
//유지보수 어려워짐