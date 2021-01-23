package com.galen.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * 用户表
 *
 * @author wcyong
 * @date 2019-04-08
 */
@Data
@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class SysUser {

    /**
     * hrID
     */
    @Id
    private Long id;

    /**
     * 是否正常（0：异常；1：正常）
     */
    @Column
    private Boolean enabled;

    /**
     * 用户名
     */
    @Column(name = "username", length = 128)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "created_by", length = 128)
    private String createdBy;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "modify_by", length = 128)
    private String modifiedBy;

    @Column(name = "modified_on")
    private Date modifiedOn;

    @Column(name = "is_deleted", length = 1)
    private Integer isDeleted;

}