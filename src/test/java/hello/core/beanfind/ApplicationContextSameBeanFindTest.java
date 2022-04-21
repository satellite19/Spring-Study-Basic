package hello.core.beanfind;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationContextSameBeanFindTest {
//	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(); //IllegalStateException
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class); // AppConfig.class 지우고 static class 생성 //	NoUniqueBeanDefinitionException 발생


	@Test
	@DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 중복 오류 발생")
	void findBeanByTypeDuplicate() {
		assertThrows(NoUniqueBeanDefinitionException.class,
			() -> ac.getBean(MemberRepository.class));
	}

	@Test
	@DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름을 지정")
	void findBeanByname() {
		MemberRepository memberRepository = ac.getBean("memberRepository1",MemberRepository.class);
		assertThat(memberRepository).isInstanceOf(MemberRepository.class);
		assertThrows(NoUniqueBeanDefinitionException.class,
			() -> ac.getBean(MemberRepository.class));
	}

	@Test
	@DisplayName("특정 타입을 모두 조회")
	void findAllBeanByType() {
		Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
		for (String key : beansOfType.keySet()) {
			System.out.println("key = " + key + " value = " + beansOfType.get(key));
		}
//		@Autowired 개념을 이해하는데 필요함
		System.out.println("beansOfType = " + beansOfType);
		assertThat(beansOfType.size()).isEqualTo(2);

	}

	@Configuration
	static class SameBeanConfig { // 해당 클래스 안의 범위에서만 사용하겠다!

		@Bean
		public MemberRepository memberRepository1() {
			return new MemoryMemberRepository();

		}
		@Bean
		public MemberRepository memberRepository2() {
			return new MemoryMemberRepository();
		}

	}

}
