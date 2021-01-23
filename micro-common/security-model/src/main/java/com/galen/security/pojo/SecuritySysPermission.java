package com.galen.security.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Galen
 * @Date: 2019/4/4-10:41
 * @Description:  适配 Security 权限
**/
public class SecuritySysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private String title;
    private List<SecuritySysRole> roles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SecuritySysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SecuritySysRole> roles) {
        this.roles = roles;
    }
}
