package com.galen.micro.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        //=====添加head参数start============================
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("AccessToken令牌，当前兼容security框架，非必填").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        // =========添加head参数end===================
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                //过滤生成链接
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    //api接口作者相关信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("ChenZhaoXi", "", "17722515203@163.com");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .license("Apache License Version 2.0")
                .title("apl-pgs-sso")
                .description("单点登陆接口服务接口文档，路由访问前缀为 sso")
                .contact(contact)
                .version("1.0.0")
                .build();
        return apiInfo;
    }

}
