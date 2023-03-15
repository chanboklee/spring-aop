package com.lee.aop;

import com.lee.aop.order.aop.AspectV1;
import com.lee.aop.order.aop.AspectV2;
import com.lee.aop.order.aop.AspectV3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
// @Import(AspectV1.class)
// @Import(AspectV2.class)
@Import(AspectV3.class)
public class SpringAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAopApplication.class, args);
    }

}
