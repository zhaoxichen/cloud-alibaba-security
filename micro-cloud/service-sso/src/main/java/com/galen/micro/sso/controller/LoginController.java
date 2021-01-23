package com.galen.micro.sso.controller;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sso.pojo.GalenWebMvcWrite;
import com.galen.micro.sso.service.LoginService;
import com.galen.micro.sso.service.LoginWechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "登录", tags = "登录，注销等操作")
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginWechatService loginWechatService;

    @ApiOperation(value = "普通登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "随机码", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("")
    public AplResponse loginNormal(String username, String password, String code, HttpServletRequest request) {
        if (null == username) {
            return ResponseUtils.FAIL("请传入username");
        }
        if (null == password) {
            return ResponseUtils.FAIL("请传入password");
        }
        if (null == code) {
            return ResponseUtils.FAIL("请传入code");
        }
        return loginService.loginNormal(username, password, code, request);
    }

    @ApiOperation(value = "手机号+密码登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "随机码", dataType = "string", paramType = "query", required = true)
    })
    @PostMapping("/mobile")
    public AplResponse loginMobilePhone(String mobile, String password, String code, HttpServletRequest request) {
        if (null == mobile) {
            return ResponseUtils.FAIL("请传入mobile");
        }
        if (null == password) {
            return ResponseUtils.FAIL("请传入password");
        }
        if (null == code) {
            return ResponseUtils.FAIL("请传入code");
        }
        return loginService.loginMobilePhone(mobile, password, code, request);
    }

    @ApiOperation(value = "注销")
    @PostMapping("/logout")
    public AplResponse logout(HttpServletRequest request) {
        return loginService.logout(request);
    }

    @ApiOperation(value = "微信扫码登陆-请求生成网页二维码")
    @GetMapping("/scan/wechat/get")
    public AplResponse openWeChatLoginScan() {
        return loginWechatService.openWeChatLogin();
    }

    @ApiIgnore(value = "微信扫码登陆-回调方法")
    @PostMapping("/scan/wechat/callback")
    public void loginScanWechat(String code, String state, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        if (null == code) {
            new GalenWebMvcWrite().writeToWeb(request, response, ResponseUtils.FAIL("请传入code"));
            return;
        }
        if (null == state) {
            new GalenWebMvcWrite().writeToWeb(request, response, ResponseUtils.FAIL("请传入username"));
            return;
        }
        loginService.loginScanWechatCallback(code, state, request, response);
    }

    @ApiOperation(value = "QQ扫码登陆-请求生成网页二维码")
    @GetMapping("/scan/qq/get")
    public AplResponse openQQLoginScan() {
        return ResponseUtils.SUCCESS("未开发!");
    }

    @ApiIgnore(value = "QQ扫码登陆-回调方法")
    @PostMapping("/scan/qq/callback")
    public AplResponse loginScanQQ(String username, String password, String code, HttpServletRequest request) {
        request.getSession().setAttribute("username", username);
        System.out.println(username + "------" + password);
        return ResponseUtils.SUCCESS("未开发!");
    }


}
