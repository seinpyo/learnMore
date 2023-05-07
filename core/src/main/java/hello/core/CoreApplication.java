package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication 안에 @ComponentScan이 포함되어 있어서 스프링 시작 시 자동으로 컴포넌트들이 스프링 빈으로 등록 되는 것
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
