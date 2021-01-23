package com.galen.scheme;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(TestConfig.class)//上面第一点配置文件类
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() throws Exception{
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.xxx.xxx.mapper.*;com.gitee.sunchenbin.mybatis.actable.dao.*");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //com.xxx.xxx.mapper.*替换成你的mapper地址
        //com.gitee.sunchenbin.mybatis.actable.dao.*固定的包
        return mapperScannerConfigurer;
    }

}