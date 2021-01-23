package com.galen.micro.zuul.api.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Galen
 * @Date: 2019/5/20-10:58
 * @Description: 路由代理其他子服务的接口文档
 **/

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        resources.add(swaggerResource("单点登陆", "/service-sso/v2/api-docs", "1.0"));
        resources.add(swaggerResource("系统基础接口", "/service-sys/v2/api-docs", "1.0"));
        resources.add(swaggerResource("用户", "/service-user/v2/api-docs", "1.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
