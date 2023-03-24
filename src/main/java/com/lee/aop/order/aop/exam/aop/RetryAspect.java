package com.lee.aop.order.aop.exam.aop;

import com.lee.aop.order.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) {
        log.info("[retry] -> {} args -> {}", joinPoint.getSignature(), retry);
        int maxRetry = retry.value();

        try {
            return joinPoint.proceed();
        }catch (Exception e){

        }

    }
}
