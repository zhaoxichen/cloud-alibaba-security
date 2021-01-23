package com.galen.micro.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableDiscoveryClient
@MapperScan("com.galen.micro.sso.mapper")
@SpringBootApplication
public class SsoApplication {

    private static final Logger log = LoggerFactory.getLogger(SsoApplication.class);

    @GetMapping("/info")
    public String swaggerInfo() {
        return "redirect:/swagger-ui.html";
    }

    public static void main(String[] args) {
        log.debug("单点登陆服务启动。。。。。 ");
        SpringApplication.run(SsoApplication.class, args);
    }

}
