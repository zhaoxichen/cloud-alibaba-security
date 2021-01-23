package com.galen.micro.sso.service;

import com.galen.model.AplResponse;

public interface ConfigService {

    /**
     * @Author: Galen
     * @Description: 获取当前用户的菜单
     * @Date: 2019/4/18-20:04
     * @Param: []
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getMenusByUser();

    /**
     * @Author: Galen
     * @Description: 获取当前用户的当前菜单页面的按钮
     * @Date: 2019/4/19-11:42
     * @Param: [menuId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getButtonByUser(Long menuId);
}
