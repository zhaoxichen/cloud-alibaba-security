package com.galen.micro.sso.service.impl;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sso.service.LoginWechatService;
import com.galen.micro.sso.utils.WxConfigConstant;
import com.galen.utils.AplUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginWechatServiceImpl implements LoginWechatService {

    //app_id
    @Value("${wechat.config.app-id:}")
    private String APP_ID;
    /**
     * @Author: Galen
     * @Description: 平台授权域url和平台设置的回调uri， 回调地址
     **/
    @Value("${wechat.callback.application-url:}")
    private String APPLICATION_URL;
    @Value("${wechat.callback.authorize:}")
    private String REDIRECT_LOGIN_URI;

    @Override
    public AplResponse openWeChatLogin() {
        // 随机数防止csrf攻击（跨站请求伪造攻击）
        String state = AplUtil.genOrdersNo();
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //stringRedisTemplate.opsForValue().set(state, state, 30, TimeUnit.MINUTES);
        Map resultMap = new HashMap();
        try {
            String redirectUri = URLEncoder.encode(APPLICATION_URL + REDIRECT_LOGIN_URI, "UTF-8");
            String fullUrl = WxConfigConstant.CONNECT_URL +
                    "?appid=" + APP_ID +
                    "&redirect_uri=" + redirectUri +
                    "&response_type=code" +
                    "&scope=snsapi_login" +
                    "&state=" +
                    state + // 由后台自动生成
                    "#wechat_redirect";
            resultMap.put("fullUrl", fullUrl);
            resultMap.put("redirectUri", redirectUri);
            resultMap.put("state", state);
            resultMap.put("appid", APP_ID);
            return ResponseUtils.SUCCESS(resultMap);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseUtils.FAIL();
        }
    }

}
