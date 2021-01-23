package com.galen.scheme.entity;

import com.galen.scheme.BaseEntity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 用户表
 *
 * @author wcyong
 * @date 2019-04-08
 */
@Table(name = "sys_user")
@Data
public class SysUser extends BaseEntity {

    /**
     * 是否正常（0：异常；1：正常）
     */
    @Column(name = "enabled", type = MySqlTypeConstant.INT, length = 4)
    private Boolean enabled;

    /**
     * 用户名
     */
    @Column(name = "username", type = MySqlTypeConstant.VARCHAR, length = 128)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", type = MySqlTypeConstant.VARCHAR, length = 128)
    private String password;

}