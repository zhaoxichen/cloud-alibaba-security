package com.galen.micro.sso.utils;

import com.galen.micro.sso.model.UserWechatWeb;
import com.galen.micro.sso.pojo.WxToken;
import com.galen.utils.HttpUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class WxCommonUtil {

    /**
     * @Author: Galen
     * @Description: 通过code获取access_token
     * @Date: 2019/5/9-14:58
     * @Param: [code]
     * @return: com.galen.tl.pojo.WxToken
     **/
    public static WxToken getToken(String code, String appId, String appSecret) {
        WxToken wxToken = null;
        String url = WxConfigConstant.TOKEN_URL + "?appid=" + appId
                + "&secret=" + appSecret
                + "&code=" + code
                + "&grant_type=" + WxConfigConstant.GRANT_TYPE;
        // 发起GET请求获取凭证
        JSONObject jsonObject = HttpUtil.httpsRequestByJson(url, "GET", null);
        if (null != jsonObject) {
            try {
                wxToken = new WxToken();
                wxToken.setAccessToken(jsonObject.getString("access_token"));
                wxToken.setExpiresIn(jsonObject.getInt("expires_in"));
                wxToken.setRefreshToken(jsonObject.getString("refresh_token"));
                wxToken.setOpenid(jsonObject.getString("openid"));
                wxToken.setScope(jsonObject.getString("scope"));
                wxToken.setUnionid(jsonObject.getString("unionid"));
            } catch (JSONException e) {
                wxToken = null;
            }
        }
        return wxToken;
    }

    /**
     * @Author: Galen
     * @Description: 使用access_token获取用户信息
     * @Date: 2019/5/9-15:16
     * @Param: [wxToken]
     * @return: com.galen.tl.model.UserWechatWeb
     **/
    public static UserWechatWeb getUserInfo(WxToken wxToken) {
        if (null == wxToken.getAccessToken() || null == wxToken.getOpenid()) {
            return null;
        }
        UserWechatWeb userWechat = null;
        String url = WxConfigConstant.USER_INFO_URL + "?access_token=" + wxToken.getAccessToken()
                + "&openid=" + wxToken.getOpenid();
        // 发起GET请求获取凭证
        JSONObject jsonObject = HttpUtil.httpsRequestByJson(url, "GET", null);

        if (null != jsonObject) {
            try {
                userWechat = new UserWechatWeb();
                userWechat.setOpenId(wxToken.getOpenid());
                userWechat.setNickname(jsonObject.getString("nickname"));
                userWechat.setGender(jsonObject.getInt("sex"));
                userWechat.setPhotoUrl(jsonObject.getString("headimgurl"));
            } catch (JSONException e) {
                userWechat = null;
            }
        }
        return userWechat;
    }
}
