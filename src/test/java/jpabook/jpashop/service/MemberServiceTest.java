package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //GIVEN
        Member member = new Member();
        member.setName("JUNG");

        //WHEN
        Long memberId = memberService.join(member);
        Member findedMember = memberRepository.findOne(memberId);

        //THEN
        Assertions.assertEquals(member, findedMember);
    }
    
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //GIVEN
        Member member1 = new Member();
        member1.setName("JUNG");

        Member member2 = new Member();
        member2.setName("JUNG");
        
        //WHEN
        memberService.join(member1);
        memberService.join(member2);
        
        //THEN
        fail("중복된 회원이 발생합니다");
    }

}