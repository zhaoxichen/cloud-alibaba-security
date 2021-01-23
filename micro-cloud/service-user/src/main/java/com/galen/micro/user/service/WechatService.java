package com.galen.micro.user.service;

import com.galen.model.AplResponse;

public interface WechatService {

    /**
     * @Author: Galen
     * @Description: 请求绑定微信
     * @Date: 2019/5/11-10:13
     * @Param: [password]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse openWeChatBinding(String password);

    /**
     * @Author: Galen
     * @Description: 绑定回调
     * @Date: 2019/5/10-16:06
     * @Param: [code, state]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse wechatBindingRedirect(String code, String state);

    void test(Long id);

    void test123(Long id);
}
