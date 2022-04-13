package hello.core.member;


import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    /*AppConfig appConfig2 = new AppConfig();
    MemberService memberService = appConfig2.memberService();*/

    AppConfig appConfig;
    MemberService memberService;

    //TODO field 에서 AppConfig 객체 생성 못하는 이유?
    @BeforeEach
    public void beforeEach(){
        appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
