package com.galen.micro.zuul.api.feign.fallback;

import com.galen.micro.zuul.api.feign.SsoFeign;
import org.springframework.stereotype.Component;

@Component
public class SsoFeignImpl implements SsoFeign {
    @Override
    public int getDecide(String requestURI, String token) {
        return 800;
    }
}
