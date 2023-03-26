package com.lee.aop.internalcall;

import com.lee.aop.order.aop.internalcall.CallServiceV1;
import com.lee.aop.order.aop.internalcall.CallServiceV2;
import com.lee.aop.order.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
public class CallServiceV2Test {

    @Autowired
    CallServiceV2 callServiceV2;

    @Test
    void external(){
        callServiceV2.external();
    }

}
