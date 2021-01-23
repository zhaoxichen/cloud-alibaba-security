package com.galen.micro.sso.service.impl;

import com.galen.micro.sso.mapper.UserSecurityMapper;
import com.galen.micro.sso.service.PermissionService;
import com.galen.security.pojo.SecuritySysPermission;
import com.galen.security.pojo.SecuritySysRole;
import com.galen.security.pojo.SecuritySysUser;
import com.galen.utils.RedisKeyConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private UserSecurityMapper userSecurityMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final String LOGIN_ROLE_NAME = "ROLE_LOGIN";
    private final List<SecuritySysRole> sysRoleLogin = Arrays.asList(new SecuritySysRole(LOGIN_ROLE_NAME, "默认具体权限"));

    @Override
    public int getDecide(String requestURI, String token) {
        /**
         * @Author: Galen
         * @Description: 获取当前登陆的用户
         **/
        if (null == token) {
            return 801;
        }
        SecuritySysUser securitySysUser = (SecuritySysUser) redisTemplate.opsForValue().get(token);
        if (securitySysUser == null) {
            return 801;
        } else {
            //续签
            redisTemplate.expire(token, 30, TimeUnit.MINUTES);
        }
        List<SecuritySysRole> needRoleList = this.getAttributes(requestURI);
        Iterator<SecuritySysRole> iterator = needRoleList.iterator();
        List<SecuritySysRole> ownRoleList;
        SecuritySysRole needRole;
        while (iterator.hasNext()) {
            needRole = iterator.next();
            //当前请求需要的权限
            if (LOGIN_ROLE_NAME.equals(needRole.getNameEn())) {
                if (!securitySysUser.getEnabled()) {
                    return 803;
                } else {
                    return 0;
                }
            }
            //当前用户所具有的权限
            ownRoleList = securitySysUser.getRoles();
            for (SecuritySysRole ownRole : ownRoleList) {
                if (ownRole.getId().equals(needRole.getId())) {
                    return 0;
                }
            }
        }
        return 802;
    }

    /**
     * @Author: Galen
     * @Description: 返回本次访问需要的权限，可以有多个权限
     * @Date: 2019/6/19-10:13
     * @Param: [requestURI]
     * @return: java.util.List<com.galen.micro.modules.sso.model.SysRole>
     **/
    private List<SecuritySysRole> getAttributes(String requestURI) {
        List<SecuritySysPermission> permissionList = redisTemplate.opsForList().range(RedisKeyConstant.PGS_MENU_CHECK_LIST, 0, -1);
        if (null == permissionList || 0 == permissionList.size()) {
            //去数据库查询资源
            permissionList = userSecurityMapper.getAllPermission();
            if (null != permissionList) {
                log.info("重新装载数据。。。。");
                redisTemplate.opsForList().rightPushAll(RedisKeyConstant.PGS_MENU_CHECK_LIST, permissionList);
                redisTemplate.expire(RedisKeyConstant.PGS_MENU_CHECK_LIST, 3, TimeUnit.MINUTES);
            }
        }
        Iterator<SecuritySysPermission> iterator = permissionList.iterator();
        List<SecuritySysRole> roleList;
        SecuritySysPermission permission;
        while (iterator.hasNext()) {
            permission = iterator.next();
            if (antPathMatcher.match(permission.getUrl(), requestURI)) {
                roleList = permission.getRoles();
                if (null == roleList || 0 == roleList.size()) {
                    log.info("访问路径是{},添加了保护但未指定角色，需要登陆后访问", requestURI);
                    return sysRoleLogin;
                }
                log.info("访问路径是{},需要的访问权限是{}", requestURI, roleList.get(0).getNameCn());
                return roleList;
            }
        }
        /**
         * @Author: Galen
         * @Description: 如果本方法返回null的话，意味着当前这个请求不需要任何角色就能访问
         * 此处做逻辑控制，如果没有匹配上的，返回一个默认具体权限，防止漏缺资源配置
         **/
        log.info("访问路径是{},未添加的保护,需要登陆后访问", requestURI);
        return sysRoleLogin;
    }

}
