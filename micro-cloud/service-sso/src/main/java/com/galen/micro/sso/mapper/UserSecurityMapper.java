package com.galen.micro.sso.mapper;

import com.galen.security.pojo.SecuritySysMenu;
import com.galen.security.pojo.SecuritySysPermission;
import com.galen.security.pojo.SecuritySysRole;
import com.galen.security.pojo.SecuritySysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Repository
public interface UserSecurityMapper {

    /**
     * @Author: Galen
     * @Description: 查询数据库用户
     * @Date: 2019/4/18-19:29
     * @Param: [username]
     * @return: com.galen.tl.pojo.SecuritySysUser
     **/
    SecuritySysUser loadUserByUsername(String username);

    /**
     * @Author: Galen
     * @Description:
     * @Date: 2019/6/19-15:21
     * @Param: [userId]
     * @return: com.galen.micro.sso.pojo.SecuritySysUser
     **/
    SecuritySysUser loadUserByUserId(Long userId);

    /**
     * @Author: Galen
     * @Description: 根据手机号登陆
     * @Date: 2019/6/26-10:44
     * @Param: [mobile]
     * @return: com.galen.micro.sso.pojo.SecuritySysUser
     **/
    SecuritySysUser loadUserByMobilePhone(String mobile);

    /**
     * @Author: Galen
     * @Description: 查询用户的角色
     * @Date: 2019/6/19-10:42
     * @Param: [userId]
     * @return: java.util.List<com.galen.micro.sso.pojo.SecuritySysRole>
     **/
    List<SecuritySysRole> getRolesByUserId(Long userId);

    /**
     * @Author: Galen
     * @Description: 获取所有的权限菜单
     * @Date: 2019/3/27-14:34
     * @Param: []
     * @return: java.util.List<SecurityPermission>
     **/
    List<SecuritySysPermission> getAllPermission();


    /**
     * @Author: Galen
     * @Description: 获取此用户的权限菜单
     * @Date: 2019/4/18-16:20
     * @Param: [userId]
     * @return: java.util.List<com.galen.micro.mini.pojo.SecuritySysMenu>
     **/
    List<SecuritySysMenu> getMenuByUid(Long userId);

    /**
     * @Author: Galen
     * @Description: 获取当前用户的当前菜单页面的按钮
     * @Date: 2019/4/19-11:43
     * @Param: [userId, menuId]
     * @return: java.util.List<java.lang.String>
     **/
    List<String> getButtonElementIdByUid(@Param("userId") Long userId, @Param("menuId") Long menuId);
}
