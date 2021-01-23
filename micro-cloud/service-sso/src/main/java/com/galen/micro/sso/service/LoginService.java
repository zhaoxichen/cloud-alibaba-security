package com.galen.micro.sso.service;

import com.galen.model.AplResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface LoginService {
    /**
     * @Author: Galen
     * @Description: 普通登陆
     * @Date: 2019/5/29-11:00
     * @Param: [username, password, code]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse loginNormal(String username, String password, String code, HttpServletRequest request);

    /**
     * @Author: Galen
     * @Description: 手机号+密码登陆
     * @Date: 2019/6/26-10:34
     * @Param: [mobile, password, code, request]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse loginMobilePhone(String mobile, String password, String code, HttpServletRequest request);

    /**
     * @Author: Galen
     * @Description: 注销
     * @Date: 2019/6/29-11:01
     * @Param: [request]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse logout(HttpServletRequest request);

    /**
     * @Author: Galen
     * @Description: 微信扫码登陆-回调
     * @Date: 2019/6/19-15:10
     * @Param: [code, state, request, response]
     * @return: void
     **/
    void loginScanWechatCallback(String code, String state, HttpServletRequest request, HttpServletResponse response);
}
