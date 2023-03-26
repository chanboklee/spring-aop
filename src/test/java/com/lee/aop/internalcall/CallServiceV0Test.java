package com.lee.aop.internalcall;

import com.lee.aop.order.aop.internalcall.CallServiceV0;
import com.lee.aop.order.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
public class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    /**
     * external() 안에 this.internal() 을 호출하기 때문에 AOP 가 적용되지 않는다.
     */
    @Test
    void external(){
        callServiceV0.external();
    }

    @Test
    void internal(){
        callServiceV0.internal();
    }
}
