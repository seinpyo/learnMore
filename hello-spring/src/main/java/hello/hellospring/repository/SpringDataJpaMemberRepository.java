package hello.hellospring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hello.hellospring.domain.Member;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
//스프링 데이터 인터페이스가 SpringDataJpaMemberRepository에 대한 구현체를 자동으로 생성 후 스프링빈에 등록해준다
    @Override
    Optional<Member> findByName(String name);

}
