package com.galen.micro.user.service.impl;

import com.galen.model.AplResponse;
import com.galen.micro.user.dto.UserRegister;
import com.galen.micro.user.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public AplResponse createUser(UserRegister userRegister) {
        return null;
    }

}
