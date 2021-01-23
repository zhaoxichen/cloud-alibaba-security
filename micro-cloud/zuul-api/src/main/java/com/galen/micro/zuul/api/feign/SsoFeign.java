package com.galen.micro.zuul.api.feign;

import com.galen.micro.zuul.api.feign.fallback.SsoFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Galen
 * @Date: 2019/6/29-12:08
 * @Description: 单点登录微服务feign接口，声明式调用
 **/
@FeignClient(value = "service-sso", fallback = SsoFeignImpl.class)
@Component("ssoFeign")
public interface SsoFeign {

    /**
     * @Author: Galen
     * @Description: 获取当前uri的判决情况
     * @Date: 2019/7/1-10:18
     * @Param: [requestURI, token]
     * @return: int
     **/
    @RequestMapping(value = "/inside/apl/pgs/get/decide", method = RequestMethod.GET)
    int getDecide(@RequestParam("requestURI") String requestURI, @RequestParam("token") String token);
}
