package com.galen.micro.sys.component.impl;

import com.galen.micro.sys.component.CurrentSecurityUser;
import com.galen.security.pojo.SecuritySysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class CurrentSecurityUserImpl implements CurrentSecurityUser {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SecuritySysUser getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (null != token) {
            return (SecuritySysUser) redisTemplate.opsForValue().get(token);
        }
        token = request.getRequestedSessionId();
        if (null == token) {
            return null;
        }
        return (SecuritySysUser) redisTemplate.opsForValue().get(token);
    }

    @Override
    public Long getCurrentUserId() {
        SecuritySysUser securitySysUser = getCurrentUser();
        if (null == securitySysUser) {
            return null;
        }
        return securitySysUser.getId();
    }
}
