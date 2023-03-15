package com.lee.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 포인트컷을 공용으로 사용하기 위해 별도의 외부 클래스에 모아두어도 된다.
 * 외부에서 호출할 때는 포인트컷이 접근제어자를 public 으로 열어두어야 한다.
 */
public class Pointcuts {

    @Pointcut("execution(* com.lee.aop.order..*(..))") // pointcut expression
    public void allOrder(){} // pointcut signature

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    // allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
