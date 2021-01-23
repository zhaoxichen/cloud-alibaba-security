package com.galen.micro.sso.controller;

import com.galen.micro.sso.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: Galen
 * @Date: 2019/7/1-10:04
 * @Description: 内部调用，不开放swagger调试
 **/
@ApiIgnore
@RestController
@RequestMapping("/inside/apl/pgs")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("获取当前uri的判决情况")
    @GetMapping("/get/decide")
    public int getDecide(String requestURI, String token) {
        if (null == requestURI || null == token) {
            return 801;
        }
        return permissionService.getDecide(requestURI, token);
    }
}
