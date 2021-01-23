package com.galen.micro.user.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 微信授权表
 *
 * @author wcyong
 * @date 2019-04-08
 */
@Data
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

}