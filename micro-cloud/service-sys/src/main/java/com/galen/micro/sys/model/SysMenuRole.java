package com.galen.micro.sys.model;

import java.io.Serializable;

/**
 * 菜单-角色关联
 * 
 * @author wcyong
 * 
 * @date 2019-04-18
 */
public class SysMenuRole  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 菜单编号
     */
    private Long menuId;

    /**
     * 角色编号
     */
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}