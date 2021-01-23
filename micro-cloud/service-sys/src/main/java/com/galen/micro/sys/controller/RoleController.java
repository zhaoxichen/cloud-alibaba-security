package com.galen.micro.sys.controller;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sys.service.RoleService;
import com.galen.utils.StringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "角色controller", tags = {"角色操作接口"})
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("增加角色")
    @PostMapping("create")
    public AplResponse createRole(String nameEn, String nameCn) {
        if (StringUtil.isEmpty(nameEn)) {
            return ResponseUtils.FAIL("请传入角色名");
        }
        if (StringUtil.isEmpty(nameCn)) {
            return ResponseUtils.FAIL("请传入角色别名，中文名");
        }
        return roleService.createRole(nameEn, nameCn);
    }

    @ApiOperation("修改角色属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "long", paramType = "query", required = true),
            @ApiImplicitParam(name = "nameEn", value = "角色名，英文", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "nameCn", value = "角色名，中文", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("modify")
    public AplResponse modifyRole(Long roleId, String nameEn, String nameCn) {
        if (null == roleId) {
            return ResponseUtils.FAIL("请传入角色id");
        }
        if (StringUtil.isEmpty(nameEn)) {
            return ResponseUtils.FAIL("请传入角色名");
        }
        if (StringUtil.isEmpty(nameCn)) {
            return ResponseUtils.FAIL("请传入角色别名，中文名");
        }
        return roleService.modifyRole(roleId, nameEn, nameCn);
    }

    @ApiOperation("添加用户为xxx角色")
    @PostMapping("add/to")
    public AplResponse addToRole(Long userId, Long roleId) {
        if (null == userId || null == roleId) {
            return ResponseUtils.build(401, "error");
        }
        return roleService.addToRole(userId, roleId);
    }

    @ApiOperation("查看所有系统角色（管理员才可以查看）")
    @GetMapping("list/all/get")
    public AplResponse getAllSysRoleList() {
        return roleService.getAllSysRoleList();
    }

    @ApiOperation("查看系统角色，不包含独立角色（管理员才可以查看）")
    @GetMapping("list/get")
    public AplResponse getSysRoleList(@ApiParam(value = "被编辑的用户id号，可选参数") Long userId) {
        if (null == userId) {
            return roleService.getSysRoleList();
        }
        return roleService.getSysRoleList(userId);
    }

    @ApiOperation("查看xx用户的角色列表")
    @GetMapping("list/user/get")
    public AplResponse getUserSysRoleList(Long userId) {
        if (null == userId) {
            return ResponseUtils.build(401, "错误");
        }
        return roleService.getUserSysRoleList(userId);
    }

    @ApiOperation("移除xx用户的一个角色")
    @PostMapping("/user/remove")
    public AplResponse removeUserSysRole(Long userId, Long roleId) {
        if (null == userId || null == roleId) {
            return ResponseUtils.build(401, "错误");
        }
        return roleService.removeUserSysRole(userId, roleId);
    }

    @ApiOperation("移除一个角色")
    @PostMapping("/remove")
    public AplResponse removeSysRole(Long roleId) {
        if (null == roleId) {
            return ResponseUtils.build(401, "错误");
        }
        return roleService.removeSysRole(roleId);
    }

    @ApiOperation("移除独立角色")
    @PostMapping("/remove/alone")
    public AplResponse removeAloneSysRole(Long userId) {
        if (null == userId) {
            return ResponseUtils.build(401, "错误");
        }
        return roleService.removeAloneSysRole(userId);
    }
}
