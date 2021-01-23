package com.galen.micro.user.service.impl;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.user.component.CurrentSecurityUser;
import com.galen.micro.user.mapper.UserSecurityMapper;
import com.galen.micro.user.mapper.UserWechatMapper;
import com.galen.micro.user.model.UserWechatWeb;
import com.galen.micro.user.pojo.WxToken;
import com.galen.micro.user.service.WechatService;
import com.galen.micro.user.utils.BCryptUtil;
import com.galen.micro.user.utils.WxCommonUtil;
import com.galen.micro.user.utils.WxConfigConstant;
import com.galen.security.pojo.SecuritySysUser;
import com.galen.utils.AplUtil;
import com.galen.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private CurrentSecurityUser currentSecurityUser;
    @Autowired
    private UserSecurityMapper userSecurityMapper;
    @Autowired
    private UserWechatMapper userWechatMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    //app_id
    @Value("${wechat.config.app-id:}")
    private String APP_ID;
    //密钥
    @Value("${wechat.config.app-secret:}")
    private String APP_SECRET;
    /**
     * @Author: Galen
     * @Description: 平台授权域url和平台设置的回调uri， 回调地址
     **/
    @Value("${wechat.callback.application-url:}")
    private String APPLICATION_URL;
    @Value("${wechat.callback.binding:}")
    private String REDIRECT_BIND_URI;

    @Override
    public AplResponse openWeChatBinding(String password) {
        Long userId = currentSecurityUser.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        SecuritySysUser securitySysUser = userSecurityMapper.loadUserByUserId(userId);
        if (null == securitySysUser) {
            return ResponseUtils.build(501, "账户异常!");
        }
        String passwordCheck = securitySysUser.getPassword();
        if (null == passwordCheck) {
            return ResponseUtils.build(501, "账户异常,密码为空!");
        }
        if (!BCryptUtil.matches(password, passwordCheck)) {
            return ResponseUtils.build(502, "密码错误，请重试!");
        }
        String state = AplUtil.genOrdersNo();
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        redisTemplate.opsForValue().set(state, userId, 30, TimeUnit.MINUTES);
        Map resultMap = new HashMap();
        try {
            String redirectUri = URLEncoder.encode(APPLICATION_URL + REDIRECT_BIND_URI, "UTF-8");
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
            return ResponseUtils.build(505, "获取验证码失败!");
        }
    }

    @Override
    @Transactional
    public AplResponse wechatBindingRedirect(String code, String state) {
        Long userId;
        try {
            userId = (Long) redisTemplate.opsForValue().get(state);
        } catch (ClassCastException e) {
            Integer temp = (Integer) redisTemplate.opsForValue().get(state);
            userId = temp.longValue();
        }
        System.out.println("userId>>>" + userId);
        if (null == userId) {
            return ResponseUtils.build(501, "state已失效！");
        }
        redisTemplate.delete(state);
        /**
         * @Author: Galen
         * @Description: 查看用户是否存在
         **/
        if (null == userWechatMapper.selectByUserId(userId)) {
            return ResponseUtils.build(502, "用户不存在!");
        }
        /**
         * @Author: Galen
         * @Description: 通过code获取access_token
         **/
        WxToken wxToken = WxCommonUtil.getToken(code, APP_ID, APP_SECRET);
        if (null == wxToken) {
            return ResponseUtils.build(503, "获取微信授权信息失败！");
        }
        UserWechatWeb userWechatUpdate = WxCommonUtil.getUserInfo(wxToken);
        if (null == userWechatUpdate) {
            return ResponseUtils.build(505, "获取微信个人信息失败！");
        }
        /**
         * @Author: Galen
         * @Description: 查看用户是否绑定过，（如果已经绑定，满足条件后修改绑定）
         **/
        UserWechatWeb userWechatBind = userWechatMapper.selectById(userId);
        if (null == userWechatBind) {
            System.out.println("初次绑定！");
        } else if (wxToken.getOpenid().equals(userWechatBind.getOpenId())) {
            /**
             * @Author: Galen
             * @Description: 已经绑定，更新一下个人信息后返回
             **/
            userWechatBind.setNickname(userWechatUpdate.getNickname());
            userWechatBind.setPhotoUrl(userWechatUpdate.getPhotoUrl());
            userWechatMapper.updateById(userWechatBind);
            return ResponseUtils.SUCCESS("绑定成功！");
        } else {
            /**
             * @Author: Galen
             * @Description: 更改绑定，使之前的绑定指向一个新的随机id
             **/
            userWechatBind.setUserId(IdUtil.generateNumberId());
            userWechatMapper.updatePrimaryKeyByOpenId(userWechatBind);
        }
        /**
         * @Author: Galen
         * @Description: 查看微信是否授权过
         **/
        UserWechatWeb userWechat = userWechatMapper.selectByOpenId(wxToken.getOpenid());
        if (null == userWechat) {
            userWechatUpdate.setUserId(userId);
            userWechatUpdate.setUnionId(wxToken.getUnionid());
            userWechatUpdate.setOpenId(wxToken.getOpenid());
            userWechatMapper.insert(userWechatUpdate); //绑定
            return ResponseUtils.SUCCESS("绑定成功！");
        }
        /**
         * @Author: Galen
         * @Description: 微信已经绑定过其他账号, 自动替换绑定到当前账户
         **/
        userWechat.setUserId(userId);
        userWechat.setNickname(userWechatUpdate.getNickname());
        userWechat.setPhotoUrl(userWechatUpdate.getPhotoUrl());
        userWechatMapper.updatePrimaryKeyByOpenId(userWechat); //修改绑定
        return ResponseUtils.SUCCESS("绑定成功！");
    }

    @Async("asyncPgsExecutor")
    @Override
    public void test(Long id) {
        UserWechatWeb userWechat = new UserWechatWeb();
        userWechat.setUserId(id);
        for (int i = 0; i < 565; i++) {
            userWechat.setPhone(String.valueOf(i));
            userWechatMapper.updateById(userWechat);
            System.out.println("更新了手机号");
        }
    }

    @Async("asyncPgsExecutor")
    @Override
    public void test123(Long id) {
        UserWechatWeb userWechat = new UserWechatWeb();
        userWechat.setUserId(id);
        for (int i = 0; i < 1000; i++) {
            userWechat.setNickname(String.valueOf(i));
            userWechatMapper.updateById(userWechat);
            System.out.println("更新了昵称");
        }
    }

}
