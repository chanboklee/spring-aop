package com.lee.aop.proxyvs;

import com.lee.aop.order.aop.member.MemberService;
import com.lee.aop.order.aop.member.MemberServiceImpl;
import com.lee.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


/**
 * @SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})
 * JDK proxy 는 MemberService 인터페이스를 기반으로 만들어진다.
 * 따라서 MemberServiceImpl 타입이 뭔지 전혀 모른다.
 * 그래서 해당 타입에 주입할 수 없다.
 * MemberServiceImpl = JDK proxy 가 성립하지 않는다.
 *
 * @SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})
 * CGLIB proxy 는 MemberServiceImpl 구체 클래스를 기반으로 만들어진다.
 * MemberServiceImpl 은 MemberService 인터페이스를 구현했기 때문에 해당 타입으로 캐스팅 할 수 있다.
 * MemberService = CGLIB proxy 가 성립
 * CGLIB proxy 는 MemberServiceImpl 구체클래스를 기반으로 만들어진다.
 * 따라서 해당 타입으로 캐스팅 할 수 있다.
 * MemberServiceImpl = CGLIB proxy 가 성
 *
 * [정리]
 * -> JDK 동적 프록시는 대상 객체인 MemberServiceImpl 타입에 의존관계를 주입할 수 없다.
 * -> CGLIB 프록시는 대상 객체인 MemberServiceImpl 타입에 의존관계를 주입할 수 있다.
 */
@Slf4j
// @SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})   // JDK 동적 프록시
// @SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})   // CGLIB 프록시
@SpringBootTest
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go(){
        log.info("memberService class -> {}", memberService.getClass());
        log.info("memberServiceImpl class -> {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }

}
