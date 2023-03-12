package com.lee.aop;

import com.lee.aop.order.OrderRepository;
import com.lee.aop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    /**
     * AopUtils.isAopProxy 을 통해서 AOP 프록시가 적용 되었는지 확인
     */
    @Test
    void aopInfo(){
        log.info("isAopProxy, orderService -> {}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository -> {}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success(){
        orderService.orderItem("itemA");
    }

    @Test
    void exception(){
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }
}
