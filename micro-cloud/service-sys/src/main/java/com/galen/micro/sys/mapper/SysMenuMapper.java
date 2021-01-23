package com.galen.micro.sys.mapper;


import com.galen.micro.sys.model.MenuFilter;
import com.galen.micro.sys.model.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * @Author: Galen
     * @Description: 更新父级id
     * @Date: 2019/4/25-12:07
     * @Param: [sysMenu]
     * @return: int
     **/
    int updateParentId(SysMenu sysMenu);

    /**
     * @Author: Galen
     * @Description: 获取当前系统权限菜单列表
     * @Date: 2019/4/20-12:03
     * @Param: [userId]
     * @return: java.util.List<com.galen.tl.model.SysMenu>
     **/
    List<SysMenu> getSysTreeMenuList(Long userId);

    /**
     * @Author: Galen
     * @Description: 获取当前系统权限菜单列表
     * @Date: 2019/4/20-12:03
     * @Param: [userId]
     * @return: java.util.List<com.galen.tl.model.SysMenu>
     **/
    List<SysMenu> getSysMenuList(Long userId);

    /**
     * @Author: Galen
     * @Description: 获取当前系统权限菜单列表
     * @Date: 2019/5/9-10:53
     * @Param: [menuFilter]
     * @return: java.util.List<com.galen.tl.model.SysMenu>
     **/
    List<SysMenu> getAllSysMenuList(MenuFilter menuFilter);

    /**
     * @Author: Galen
     * @Description: 获取总条数
     * @Date: 2019/5/9-11:22
     * @Param: [menuFilter]
     * @return: long
     **/
    long getRowsTotal(MenuFilter menuFilter);

    /**
     * @Author: Galen
     * @Description: 查询当前角色的权限
     * @Date: 2019/4/20-14:09
     * @Param: [roleId]
     * @return: java.util.List<com.galen.tl.model.SysMenu>
     **/
    List<SysMenu> getSysMenuListByRoleId(Long roleId);

    /**
     * @Author: Galen
     * @Description: 获取此用户的菜单id
     * @Date: 2019/4/24-16:31
     * @Param: [roleId]
     * @return: java.util.List<java.lang.Long>
     **/
    List<Long> getSysMenuIdListByRoleId(Long roleId);

    /**
     * @Author: Galen
     * @Description: 查询一批角色所拥有的权限id(去重复)
     * @Date: 2019/4/24-12:01
     * @Param: [roleList]
     * @return: java.util.List<java.lang.Long>
     **/
    List<Long> getSysMenuListByRoleIdList(@Param("roleList") List<Long> roleList);

    /**
     * @Author: Galen
     * @Description: 获取一层子菜单
     * @Date: 2019/5/9-16:26
     * @Param: [menuId]
     * @return: java.util.List<java.lang.Long>
     **/
    List<Long> selectByPid(Long menuId);
}