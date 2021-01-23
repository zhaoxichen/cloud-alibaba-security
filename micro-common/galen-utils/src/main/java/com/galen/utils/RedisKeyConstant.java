package com.galen.utils;

/**
 * @Author: Galen
 * @Date: 2019/4/11-17:54
 * @Description: redis常量
 **/
public interface RedisKeyConstant {

    String PGS_LOGIN_USER = "pgs_login_user_";// pgs用户登录

    String PGS_MINI_PHONE_BIND_CODE = "pgs_mini_phone_bind_code_";// pgs小程序，手机绑定验证码

    String PGS_MINI_MAIL_BIND_CODE = "pgs_mini_mail_bind_code_";// pgs小程序，邮箱绑定验证码

    String PGS_COUNTDOWN_TURNTABLE_TOPIC_TASK = "pgs_countdown_turntable_topic_task_";// 活动主题倒计时
    String PGS_COUNTDOWN_TURNTABLE_TOPIC_TASK_SET = "pgs_countdown_turntable_topic_task_set";// 活动主题倒计时

    String PGS_MENU_CHECK_LIST = "pgs_menu_check_list";// pgs权限验证

    String TLMS_MENU_CHECK_LIST = "tlms_menu_check_list";// tl权限验证

    //普通的同步锁
    String SYNCHRONIZATION_LOCK = "synchronization_lock_";
}
