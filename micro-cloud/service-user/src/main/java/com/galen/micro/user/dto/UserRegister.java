package com.galen.micro.user.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * @Author: Galen
 * @Date: 2019/6/25-16:35
 * @Description: 用户注册
 **/
@Data
public class UserRegister {

    @ApiParam(value = "用户名", required = true)
    private String username;

    @ApiParam(value = "密码", required = true)
    private String password;

    @ApiParam(value = "中文名", required = true)
    private String fullNameCn;

    @ApiParam(value = "英文名", required = true)
    private String fullNameEn;

    @ApiParam(value = "是否独立角色", required = true)
    private Boolean onAlone;

    @ApiParam(value = "角色数组", required = true)
    private List<Long> roleList;
}
