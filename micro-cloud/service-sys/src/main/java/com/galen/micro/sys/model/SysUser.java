package com.galen.micro.sys.model;

import java.io.Serializable;

/**
 * 用户表
 * 
 * @author wcyong
 * 
 * @date 2019-04-08
 */
public class SysUser  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * hrID
     */
    private Long id;

    /**
     * 是否正常（0：异常；1：正常）
     */
    private Boolean enabled;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}