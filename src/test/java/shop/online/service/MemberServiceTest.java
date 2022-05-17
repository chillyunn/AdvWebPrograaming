package shop.online.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.online.domain.Member;
import shop.online.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired MemberService memberService;
    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("kim");
        Long savedId = memberService.join(member);
        System.out.println("savedId = " + savedId);
        assertThat(member).as(()->"가입된 회원의 조회 결과는 같아야 한다")
                .isEqualTo(memberService.findOne(savedId));
    }
    @Test
    void 중복_회원_예외() throws Exception{
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);

        assertThatThrownBy( ()->{
            memberService.join(member2);
        },"중복 예외가 발생해야 한다")
                .isInstanceOf(IllegalStateException.class);
    }
    @Test
    void 중복_회원_예외_BDD() throws Exception{
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        memberService.join(member1);
        Throwable thrown = catchThrowable(() -> memberService.join(member2));

        assertThat(thrown).isInstanceOf(IllegalStateException.class);
    }
}