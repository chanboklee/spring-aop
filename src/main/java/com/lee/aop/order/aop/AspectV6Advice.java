package com.lee.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

/**
 * 스프링은 5.2.7 버전부터 동일한 @Aspect 안에서 동일한 조인포인트의 우선순위를 정했다.
 * 실행 순서 : @Around, @Before, @After, @AfterReturning, @AfterThrowing
 */
@Slf4j
@Aspect
public class AspectV6Advice {

    @Around("com.lee.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{

        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();

            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        }catch (Exception e){
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        }finally {
            // @After
            log.info("[트랜잭션 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    /**
     * @Around 와 다르게 작업 흐름을 변경할 수 없다.
     * @Around 는 ProceedingJoinPoint.proceed() 를 호출해야 다음 대상이 호출된다. 만약 호출하지 않으면 다음 대상이 호출되지 않는다.
     * 반면에 @Before 는 proceedingJoinPoint.proceed() 자체를 사용하지 않는다.
     * 메서드 종료 시 자동으로 다음 타겟이 호출된다.
     * 물론 예외가 발생하면 다음 코드가 호출되지는 않는다.
     */
    @Before("com.lee.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint){
        log.info("[before] -> {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "com.lee.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result){
        log.info("[return] -> {}, result -> {}", joinPoint.getSignature(), result);
    }

    /**
     * returning 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
     * returning 절에 지정된 타입의 값을 반환하는 메서드만 대상으로 실행(부모 타입을 지정하면 모든 자식 타입은 인정)
     * @Around 와 다르게 반환되는 객체를 변경할 수 없다. 반환 객체를 변경하려면 @Around 를 사용
     * 참고로 반환 객체를 조작할 수는 있다.
     */
    @AfterReturning(value = "com.lee.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void doReturn(JoinPoint joinPoint, String result){
        log.info("[return] -> {}, result -> {}", joinPoint.getSignature(), result);
    }

    /**
     * throwing 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
     * throwing 절에 지정된 타입의 값을 반환하는 메서드만 대상으로 실행(부모 타입을 지정하면 모든 자식 타입은 인정)
     */
    @AfterThrowing(value = "com.lee.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex){
        log.info("[ex] -> {} message -> {}", ex);
    }

    @After(value = "com.lee.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[after] {}", joinPoint.getSignature());
    }
}
