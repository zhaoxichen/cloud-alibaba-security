package com.galen.micro.user.controller;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.user.pojo.WxWebMvcWrite;
import com.galen.micro.user.service.WechatService;
import com.galen.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "用户管理", tags = "微信用户")
@RestController
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private WechatService wechatService;

    @ApiOperation(value = "请求生成网页二维码供用户扫描绑定")
    @ApiImplicitParam(name = "password", value = "输入登陆密码", dataType = "string", paramType = "query", required = true)
    @GetMapping("/binding/get")
    public AplResponse openWeChatBinding(String password) {
        if (null == password) {
            return ResponseUtils.build(401, "请确认密码");
        }
        return wechatService.openWeChatBinding(password);
    }

    @ApiIgnore(value = "绑定微信")
    @GetMapping("/binding/callback")
    public void wechatBinding(HttpServletRequest request, HttpServletResponse response, String code, String state) throws IOException {
        if (StringUtil.isEmpty(code)) {
            new WxWebMvcWrite().writeToWeb(request, response, 4);
            return;
        }
        if (null == state) {
            new WxWebMvcWrite().writeToWeb(request, response, 4);
            return;
        }
        AplResponse result = wechatService.wechatBindingRedirect(code, state);
        if (200 == result.getStatus()) {
            new WxWebMvcWrite().writeToWeb(request, response, 3);
        } else {
            new WxWebMvcWrite().writeToWeb(request, response, 4);
        }
    }

    @GetMapping("/test")
    public AplResponse test(Long id) {
        wechatService.test(id);
        wechatService.test123(id);
        return ResponseUtils.SUCCESS();
    }
}
