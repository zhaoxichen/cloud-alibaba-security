package com.galen.micro.sso.service;

public interface PermissionService {
    /**
     * @Author: Galen
     * @Description: 获取当前uri的判决情况
     * @Date: 2019/7/1-10:17
     * @Param: [requestURI, token]
     * @return: int
     **/
    int getDecide(String requestURI, String token);
}
