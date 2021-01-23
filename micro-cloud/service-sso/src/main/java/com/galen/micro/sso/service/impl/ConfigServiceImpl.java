package com.galen.micro.sso.service.impl;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sso.component.CurrentSecurityUser;
import com.galen.micro.sso.mapper.UserSecurityMapper;
import com.galen.micro.sso.service.ConfigService;
import com.galen.security.pojo.SecuritySysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private CurrentSecurityUser currentSecurityUser;
    @Autowired
    private UserSecurityMapper userSecurityMapper;

    @Override
    public AplResponse getMenusByUser() {
        Long userId = currentSecurityUser.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        List<SecuritySysMenu> securitySysMenuList = userSecurityMapper.getMenuByUid(userId);
        return ResponseUtils.SUCCESS(securitySysMenuList);
    }

    @Override
    public AplResponse getButtonByUser(Long menuId) {
        Long userId = currentSecurityUser.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        List<String> buttonIdList = userSecurityMapper.getButtonElementIdByUid(userId, menuId);
        return ResponseUtils.SUCCESS(buttonIdList);
    }

}
