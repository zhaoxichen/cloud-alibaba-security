package com.galen.order.controller;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.order.domain.UserRepository;
import com.galen.order.entity.SysUser;
import com.galen.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("add")
    public AplResponse addUser() {
        SysUser sysUser = new SysUser();
        sysUser.setId(IdUtil.generateNumberId());
        sysUser.setUsername("123");
        sysUser.setPassword("123456");
        sysUser.setCreatedBy("1");
        sysUser.setModifiedBy("1");
        userRepository.save(sysUser);
        return ResponseUtils.build(sysUser);
    }

    @GetMapping("get/{id}")
    public AplResponse getUser(@PathVariable("id") Long id) {
        SysUser sysUser = userRepository.getOne(id);
        return ResponseUtils.build(sysUser);
    }
}
