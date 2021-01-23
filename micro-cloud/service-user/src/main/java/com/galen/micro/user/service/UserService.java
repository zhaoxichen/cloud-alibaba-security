package com.galen.micro.user.service;

import com.galen.model.AplResponse;
import com.galen.micro.user.dto.UserRegister;

public interface UserService {

    /**
     * @Author: Galen
     * @Description: 创建一个新用户
     * @Date: 2019/6/25-16:50
     * @Param: [userRegister]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse createUser(UserRegister userRegister);

}
