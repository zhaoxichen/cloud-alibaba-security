package com.galen.security.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Galen
 * @Date: 2019/3/27-15:54
 * @Description:
 **/
public class SecuritySysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long id;


    private Boolean enabled;

    private String nicknameCn;

    private String nicknameEn;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String userface;
    /**
     * 登陆凭证
     */
    private String token;

    private List<SecuritySysRole> roles;

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

    public String getNicknameCn() {
        return nicknameCn;
    }

    public void setNicknameCn(String nicknameCn) {
        this.nicknameCn = nicknameCn;
    }

    public String getNicknameEn() {
        return nicknameEn;
    }

    public void setNicknameEn(String nicknameEn) {
        this.nicknameEn = nicknameEn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<SecuritySysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SecuritySysRole> roles) {
        this.roles = roles;
    }
}