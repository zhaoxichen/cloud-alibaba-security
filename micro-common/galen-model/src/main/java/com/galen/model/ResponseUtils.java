package com.galen.model;

/**
 * @Author: Galen
 * @Date: 2019/3/6-16:25
 * @Description: 对象统一返回格式工具类
 * 应遵循：
 * 成功逻辑码  200
 * 登陆信息失效码 801
 * controller层错误码 401起499止
 * service层错误码 501起599止
 **/
public class ResponseUtils {

    public static AplResponse SUCCESS() {
        return new AplResponse(200, "SUCCESS", null);
    }

    public static AplResponse SUCCESS(Object bean) {
        return new AplResponse(200, "SUCCESS", bean);
    }

    public static AplResponse SUCCESS(Object bean, long total) {
        return new AplResponse(total, bean);
    }

    public static AplResponse FAIL() {
        return new AplResponse(400, "FAIL", null);
    }

    public static AplResponse FAIL(String msg) {
        return new AplResponse(400, msg, null);
    }

    public static AplResponse build(Integer status, String msg) {
        return new AplResponse(status, msg, null);
    }

    public static AplResponse build(Object bean) {
        return new AplResponse(200, "SUCCESS", bean);
    }

    public static AplResponse build(Integer status, String msg, Object bean) {
        return new AplResponse(status, msg, bean);
    }

    /**
     * 身份失效
     *
     * @return
     */
    public static AplResponse invalid() {
        return new AplResponse(801, "登录信息失效，请重新登录", null);
    }

}

