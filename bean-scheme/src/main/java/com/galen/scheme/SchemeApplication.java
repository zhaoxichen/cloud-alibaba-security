package com.galen.scheme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchemeApplication {

    private static final Logger log = LoggerFactory.getLogger(SchemeApplication.class);

    public static void main(String[] args) {
        log.debug("beanToScheme服务启动。。。。。 ");
        SpringApplication.run(SchemeApplication.class, args);
    }

}
