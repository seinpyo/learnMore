package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)    //스프링과 함께 테스트를 실행하기 위한 어노테이션1
@SpringBootTest //스프링과 함께 테스트를 실행하기 위한 어노테이션2
@Transactional  //테스트 코드에 붙으면 마지막에 롤백시킴
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
            //flush가 될 때 JAP 영속성 컨텍스트에 있는 member 객체가 insert 쿼리에 실려서 db로 감 -> 보통 트렌젝션이 commit될 때 flush됨
            //로그에서 보기 위해서 commit은 안하고 flush만 해본 것 -> 트렌젝션은 롤백되기 때문에 db에 반영 x
        Assertions.assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)   //해당 예외가 발생하면 테스트 성공
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("lee");

        Member member2 = new Member();
        member2.setName("lee");

        //when
        memberService.join(member1);
        memberService.join(member2);    //예외가 발생해야 됨

        //then
        fail("예외가 발생해야 합니다");    //예외가 발생을 안하고 이 코드가 실행되면 잘못된 테스트인거
    }
}