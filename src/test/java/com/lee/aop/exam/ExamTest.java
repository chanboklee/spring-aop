package com.lee.aop.exam;

import com.lee.aop.order.aop.exam.ExamService;
import com.lee.aop.order.aop.exam.aop.RetryAspect;
import com.lee.aop.order.aop.exam.aop.TraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
// @Import(TraceAspect.class)
@Import({TraceAspect.class, RetryAspect.class})
@SpringBootTest
public class ExamTest {

    @Autowired
    ExamService examService;

    @Test
    void test(){
        for(int i=0; i<5; i++){
            log.info("client request i -> {}", i);
            examService.request("data"+i);
        }
    }
}
