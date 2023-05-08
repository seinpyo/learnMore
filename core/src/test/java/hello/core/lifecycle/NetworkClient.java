package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call =" + url + ", message = " + message);
    }

    //서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close = " + url);
    }

    @PostConstruct
    public void init() throws Exception {
        //프로퍼티 세팅이 끝나면 = 의존존계가 주입된 후 호출
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() throws Exception {
        //빈 소멸시 호출
        System.out.println("NetworkClient.close");
        disconnect();
    }

    //초기화, 소멸 인터페이스 단점
    //  : 스프링 전용 인터페이스에 의존하게 된다.
    //  : 초기화, 소멸 메서드의 이름 변경 x, 외부 라이브러리에 적용할 수 x

    //설정 정보 사용시
    // : 스프링 코드에 의존 x, 메서드 이름을 자유롭게 할 수 있다.
    // : 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.

    //@PostConstruct, @PreDestroy 사용을 권장
    //단 외부라이브러리에는 사용 못함! 설정 정보 (@Bean(initMethod="init", destroyMethod="close")) 사용하기

}
