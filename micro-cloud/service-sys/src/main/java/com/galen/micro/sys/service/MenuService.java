package com.galen.micro.sys.service;

import com.galen.model.AplResponse;
import com.galen.micro.sys.model.MenuFilter;

public interface MenuService {

    /**
     * @Author: Galen
     * @Description: 添加一个权限菜单
     * @Date: 2019/4/19-14:06
     * @Param: [menuType, parentId, title,titleEn, iconPic, path, component, elementId, requestUrl, sortOrder]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse createSysMenu(Integer menuType, Long parentId, String title, String titleEn, String iconPic, String path, String component,
                              String elementId, String requestUrl, Integer sortOrder);

    /**
     * @Author: Galen
     * @Description: 更新权限菜单
     * @Date: 2019/4/19-15:22
     * @Param: [id, title, titleEn, iconPic, path, component, elementId, requestUrl, sortOrder]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse modifySysMenu(Long id, String title, String titleEn, String iconPic, String path, String component, String elementId,
                              String requestUrl, Integer sortOrder);

    /**
     * @Author: Galen
     * @Description: 给角色添加权限
     * @Date: 2019/4/19-10:30
     * @Param: [roleId, menuId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse addToSysMenu(Long roleId, Long menuId);

    /**
     * @Author: Galen
     * @Description: 获取当前用户的菜单
     * @Date: 2019/4/18-20:04
     * @Param: []
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getMenusByUser();

    /**
     * @Author: Galen
     * @Description: 获取当前用户的当前菜单页面的按钮
     * @Date: 2019/4/19-11:42
     * @Param: [menuId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getButtonByUser(Long menuId);

    /**
     * @Author: Galen
     * @Description: 查看系统权限资源管理(原始数据)
     * @Date: 2019/5/14-10:32
     * @Param: [menuFilter]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getAllSysMenuList(MenuFilter menuFilter);

    /**
     * @Author: Galen
     * @Description: 获取系统权限列表
     * @Date: 2019/4/19-14:53
     * @Param: []
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getSysMenuList();

    /**
     * @Author: Galen
     * @Description: 获取系统权限列表, 核查此角色是否有添加此权限
     * @Date: 2019/4/24-16:27
     * @Param: [roleId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getSysMenuList(Long roleId);

    /**
     * @Author: Galen
     * @Description: 查看xx角色的权限资源
     * @Date: 2019/4/20-14:00
     * @Param: [roleId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getRoleSysMenuList(Long roleId);

    /**
     * @Author: Galen
     * @Description: 移除xx角色的一个权限资源
     * @Date: 2019/4/20-14:03
     * @Param: [roleId, menuId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse removeRoleSysMenu(Long roleId, Long menuId);

    /**
     * @Author: Galen
     * @Description: 移除一个权限资源
     * @Date: 2019/4/20-14:17
     * @Param: [menuId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse removeSysMenu(Long menuId);
}
