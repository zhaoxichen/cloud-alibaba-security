package com.galen.micro.user.controller;

import com.galen.model.AplResponse;
import com.galen.micro.user.dto.UserRegister;
import com.galen.micro.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户管理", tags = "普通用户")
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "创建用户", notes = "创建一个全新的用户")
    @PostMapping("create")
    public AplResponse createUser(UserRegister userRegister) {
        return userService.createUser(userRegister);
    }
}
