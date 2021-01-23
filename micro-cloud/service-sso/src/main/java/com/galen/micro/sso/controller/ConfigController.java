package com.galen.micro.sso.controller;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sso.component.CurrentSecurityUser;
import com.galen.micro.sso.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "配置", tags = "主要用来获取一些配置信息，菜单信息")
@RestController
@RequestMapping("config/")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @Autowired
    CurrentSecurityUser currentSecurityUser;

    @ApiOperation("获取当前用户的系统菜单")
    @GetMapping("/current/menu")
    public AplResponse currentMenu() {
        return configService.getMenusByUser();
    }

    @ApiOperation("获取当前用户的当前菜单页面的受限制按钮元素id集合")
    @GetMapping("/current/button")
    public AplResponse currentButton(Long menuId) {
        if (null == menuId) {
            return ResponseUtils.build(401, "请传入菜单id");
        }
        return configService.getButtonByUser(menuId);
    }

    @ApiOperation("获取当前用户")
    @GetMapping("/current/user")
    public AplResponse currentUser() {
        /**
         * @Author: Galen
         * @Description: 获取request的其他信息
         **/
        return ResponseUtils.SUCCESS(currentSecurityUser.getCurrentUser());
    }
}