package com.galen.micro.user.utils;

/**
 * @Author: Galen
 * @Date: 2019/5/13-11:16
 * @Description: 微信授权相关配置
 **/
public interface WxConfigConstant {
    /**
     * @Author: Galen
     * @Description: 第三方网站引导用户打开链接
     * https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     **/
    String CONNECT_URL = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * @Author: Galen
     * @Description: 凭证获取的url（GET） 获取access_token
     * Http Get请求
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     **/
    String TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * @Author: Galen
     * @Description: 全局唯一接口调用凭据
     * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效
     * 公众号和小程序均可以使用AppID和AppSecret调用本接口来获取access_token
     **/
    String TOKEN_GLOBAL_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=client_credential";

    /**
     * @Author: Galen
     * @Description: 默认类型
     **/
    String GRANT_TYPE = "authorization_code";
    /**
     * @Author: Galen
     * @Description: 获取个人信息
     **/
    String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * @Author: Galen
     * @Description: 发送模板消息
     **/
    String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";
}
