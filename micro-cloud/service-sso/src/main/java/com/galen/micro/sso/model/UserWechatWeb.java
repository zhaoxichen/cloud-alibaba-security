package com.galen.micro.sso.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 微信授权表
 *
 * @author wcyong
 * @date 2019-04-08
 */
@TableName("user_wechat_web")
public class UserWechatWeb {
    /**
     * userId，与用户表共用主键，一对一
     */
    @TableId
    private Long userId;

    /**
     * 微信唯一id
     */
    private String unionId;

    /**
     * 平台唯一id
     */
    private String openId;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 微信昵称
     */
    private String nickname;

    /**
     * 微信头像
     */
    private String photoUrl;
    /**
     * 性别
     */
    private int gender;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }
}