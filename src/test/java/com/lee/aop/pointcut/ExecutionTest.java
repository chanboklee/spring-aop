package com.lee.aop.pointcut;

import com.lee.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException{
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod(){
        // public java.lang.String com.lee.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod -> {}", helloMethod);
    }

    /**
     * execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
     * 메소드 실행 조인 포인트를 매칭한다.
     * ?는 생략할수 있다.
     * * 같은 패턴을 지정할 수 있다.
     *
     * 접근제어자? : public
     * 반환타입 : String
     * 선언타입? : com.lee.aop.order.aop.member.MemberServiceImpl
     * 메서드이름 : hello
     * 파라미터 : String
     * 예외? : 생략
     */
    @Test
    void exactMatch(){
        // public java.lang.String com.lee.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String com.lee.aop.order.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch(){
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch(){
        pointcut.setExpression("execution(* hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1(){
        pointcut.setExpression("execution(* hel*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2(){
        pointcut.setExpression("execution(* *el*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse(){
        pointcut.setExpression("execution(* nono(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1(){
        pointcut.setExpression("execution(* com.lee.aop.order.aop.member.MemberServiceImpl.hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * com.lee.aop.order.aop.member.*(1).*(2)
     * (1) : 타입
     * (2) : 메서드 이름
     */
    @Test
    void packageExactMatch2(){
        pointcut.setExpression("execution(* com.lee.aop.order.aop.member.*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 패키지에서 '.', '..'의 차이를 이해
     * '.' : 정확하게 해당 위치의 패키지
     */
    @Test
    void packageExactFalse(){
        pointcut.setExpression("execution(* com.lee.aop.order.aop.*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    /**
     * 패키지에서 '.', '..'의 차이를 이해
     * '..' : 해당 위치의 패키지와 그 하위 패키지도 포함
     */
    @Test
    void packageMatchSubPackage1(){
        pointcut.setExpression("execution(* com.lee.aop.order.aop.member..*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2(){
        pointcut.setExpression("execution(* com.lee.aop.order.aop..*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
