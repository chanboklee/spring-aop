package com.lee.aop.pointcut;

import com.lee.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException{
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact(){
        pointcut.setExpression("within(com.lee.aop.order.aop.member.MemberServiceImpl)");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinStar(){
        pointcut.setExpression("within(com.lee.aop.order.aop.member.*Service*)");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage(){
        pointcut.setExpression("within(com.lee.aop.order.aop..*)");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 표현식에 부모 타입을 지정하면 안된다.
     * 정확하게 타입이 맞아야 한다.
     * 이 부분에서 execution 과 차이가 있다.
     */
    @Test
    @DisplayName("타겟의 타입에만 직접 적용, 인터페이스를 선정하면 안된다.")
    void withinSuperTypeFalse(){
        pointcut.setExpression("within(com.lee.aop.order.aop.member.MemberService)");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("execution 은 타입 기반, 인터페이스 선정 가능.")
    void executionSuperTypeTrue(){
        pointcut.setExpression("execution(* com.lee.aop.order.aop.member.MemberService.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
