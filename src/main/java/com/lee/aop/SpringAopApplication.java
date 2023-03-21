package com.lee.aop;

import com.lee.aop.order.aop.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
// @Import(AspectV1.class)
// @Import(AspectV2.class)
// @Import(AspectV3.class)
// @Import(AspectV4Pointcut.class)
// @Import({AspectV5Order.LogAspect.class, AspectV5Order.TransactionAspect.class})
public class SpringAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAopApplication.class, args);
    }

}
