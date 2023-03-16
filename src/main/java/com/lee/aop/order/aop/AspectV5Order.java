package com.lee.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 * 순서를 변경하기 위해서는 class 를 따로 분리해서 @Order 를 적용해야 한다.
 * 숫자가 낮은 것부터 먼저 실행한다.
 */
@Slf4j
public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect {

        @Around("com.lee.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
            log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TransactionAspect {

        @Around("com.lee.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{

            try {
                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
                return result;
            }catch (Exception e){
                log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
                throw e;
            }finally {
                log.info("[트랜잭션 리소스 릴리즈] {}", joinPoint.getSignature());
            }
        }
    }
}
