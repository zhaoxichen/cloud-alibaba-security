package com.galen.micro.zuul.api.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * 路由对user子服务的熔断器
 */
@Component
public class SsoFallbackProvider implements FallbackProvider {
    /**
     * 配置对那个被调用服务进行监听，错误时候发生熔断。  * 为所有
     *
     * @return
     */
    @Override
    public String getRoute() {
        return "service-sso";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ApiClientHttpResponseImpl("{\"status\":901,\"msg\":\"service-feign-user not available\",\"total\":0,\"bean\":\"null\"}", true);
    }

}
