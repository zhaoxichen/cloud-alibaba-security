package com.galen.micro.sso.service;

import com.galen.model.AplResponse;

public interface LoginWechatService {
    /**
     * @Author: Galen
     * @Description: 请求授权登陆
     * @Date: 2019/5/11-11:36
     * @Param: []
     * @return: com.galen.model.AplResponse
     **/
    AplResponse openWeChatLogin();

}
