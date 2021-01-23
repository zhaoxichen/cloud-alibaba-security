package com.galen.micro.sys.mapper;

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
