package com.galen.micro.user;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableDiscoveryClient
@EnableFeignClients  //feign 模式
@MapperScan("com.galen.micro.user.mapper")
@SpringBootApplication
public class UserApplication {

    private static final Logger log = LoggerFactory.getLogger(UserApplication.class);

    @GetMapping("/info")
    public String swaggerInfo() {
        return "redirect:/swagger-ui.html";
    }

    public static void main(String[] args) {
        log.debug("用户服务启动。。。。。 ");
        SpringApplication.run(UserApplication.class, args);
    }

}
