package com.lee.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Pointcut 에 포인트컷 표현식을 사용
 * 메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처(signature)
 * 메서드 반환 타입은 void
 * 코드 내용은 비워둔다
 * private, public 같은 접근 제어자는 내부에서만 사용하면 private 을 사용해도 되지만, 다른 에스펙트에서 참고하려면 public
 */
@Slf4j
@Aspect
public class AspectV2 {

    /**
     *  com.lee.aop.order 패키지와 하위 패키지
     */
    @Pointcut("execution(* com.lee.aop.order..*(..))") // pointcut expression
    private void allOrder(){} // pointcut signature

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }
}
