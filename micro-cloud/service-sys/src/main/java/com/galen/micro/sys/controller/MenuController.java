package com.galen.micro.sys.controller;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sys.annotation.SysLog;
import com.galen.micro.sys.model.MenuFilter;
import com.galen.micro.sys.service.MenuService;
import com.galen.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "权限controller", tags = {"权限菜单操作接口"})
@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @SysLog("增加权限菜单")
    @ApiOperation("增加权限菜单（把资源加入权限管理）")
    @PostMapping("create")
    public AplResponse createSysMenu(Integer menuType, Long parentId, String title, String titleEn, String iconPic, String path, String component,
                                     String elementId, String requestUrl, Integer sortOrder) {
        if (null == menuType) {
            return ResponseUtils.FAIL("请传入菜单类型：1：左侧主菜单；2：页面中的按钮；3：页面中标签");
        }
        if (null == parentId) {
            parentId = 0L;
        }
        if (StringUtil.isEmpty(title)) {
            if (1 == menuType) {
                return ResponseUtils.FAIL("请传入菜单名称");
            }
        }
        if (StringUtil.isEmpty(titleEn)) {
            if (1 == menuType) {
                return ResponseUtils.FAIL("请传入菜单英文名称");
            }
        }
        if (StringUtil.isEmpty(elementId)) {
            if (2 == menuType) {
                return ResponseUtils.FAIL("请传入元素id");
            }
        }
        if (StringUtil.isEmpty(requestUrl)) {
            requestUrl = "/";
        }
        if (null == sortOrder) {
            sortOrder = 1;
        }
        return menuService.createSysMenu(menuType, parentId, title, titleEn, iconPic, path, component, elementId, requestUrl, sortOrder);
    }

    @SysLog("更新权限菜单")
    @ApiOperation("更新权限菜单")
    @PostMapping("modify")
    public AplResponse modifySysMenu(Long id, String title, String titleEn, String iconPic, String path, String component,
                                     String elementId, String requestUrl, Integer sortOrder) {
        if (null == id) {
            return ResponseUtils.FAIL("请传入权限菜单id");
        }
        return menuService.modifySysMenu(id, title, titleEn, iconPic, path, component, elementId, requestUrl, sortOrder);
    }

    @ApiOperation("添加角色拥有xxx权限")
    @PostMapping("add/to")
    public AplResponse addToSysMenu(Long roleId, Long menuId) {
        if (null == roleId || null == menuId) {
            return ResponseUtils.build(401, "错误");
        }
        return menuService.addToSysMenu(roleId, menuId);
    }

    @ApiOperation(value = "查看系统权限资源管理", notes = "权限资源管理页面查看权限资源原始数据")
    @GetMapping("list/all/get")
    public AplResponse getAllSysMenuList(MenuFilter filter) {
        return menuService.getAllSysMenuList(filter);
    }

    @ApiOperation(value = "查看系统权限资源", notes = "如果传入roleId，则顺便核查此角色是否拥有该权限，对应的权限对象中的onChoose=true为拥有")
    @GetMapping("list/get")
    public AplResponse getSysMenuList(Long roleId) {
        if (null == roleId) {
            return menuService.getSysMenuList();
        }
        return menuService.getSysMenuList(roleId);
    }

    @ApiOperation("仅查看xx角色的权限资源列表")
    @GetMapping("list/role/get")
    public AplResponse getRoleSysMenuList(Long roleId) {
        if (null == roleId) {
            return ResponseUtils.build(401, "错误");
        }
        return menuService.getRoleSysMenuList(roleId);
    }

    @ApiOperation("移除xx角色的一个权限资源")
    @PostMapping("/role/remove")
    public AplResponse removeRoleSysMenu(Long roleId, Long menuId) {
        if (null == roleId || null == menuId) {
            return ResponseUtils.build(401, "错误");
        }
        return menuService.removeRoleSysMenu(roleId, menuId);
    }

    @ApiOperation("移除一个权限资源")
    @PostMapping("/remove")
    public AplResponse removeSysMenu(Long menuId) {
        if (null == menuId) {
            return ResponseUtils.build(401, "错误");
        }
        return menuService.removeSysMenu(menuId);
    }

}
