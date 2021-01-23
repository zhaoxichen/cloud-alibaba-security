package com.galen.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {

    private static final Logger log = LoggerFactory.getLogger(OrderApplication.class);

    public static void main(String[] args) {
        log.debug("订单服务启动。。。。。 ");
        SpringApplication.run(OrderApplication.class);
    }
}
