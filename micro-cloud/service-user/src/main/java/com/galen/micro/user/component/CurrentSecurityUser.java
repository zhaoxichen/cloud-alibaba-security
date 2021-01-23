package com.galen.micro.user.component;


import com.galen.security.pojo.SecuritySysUser;

/**
 * @Author: Galen
 * @Date: 2019/5/29-14:11
 * @Description: 当前用户
 **/

public interface CurrentSecurityUser {
    /**
     * @Author: Galen
     * @Description: 获取当前用户权限信息
     * @Date: 2019/5/29-14:10
     * @Param: []
     * @return: com.galen.micro.pojo.SecuritySysUser
     **/
    SecuritySysUser getCurrentUser();

    /**
     * @Author: Galen
     * @Description: 获取当前使用者的编号
     * @Date: 2019/5/29-14:10
     * @Param: []
     * @return: java.lang.Long
     **/
    Long getCurrentUserId();
}
