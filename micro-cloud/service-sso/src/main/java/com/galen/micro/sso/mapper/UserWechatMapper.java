package com.galen.micro.sso.mapper;

import com.galen.micro.sso.model.UserWechatWeb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserWechatMapper extends BaseMapper<UserWechatWeb> {

    /**
     * @Author: Galen
     * @Description: 根据openId查询
     * @Date: 2019/4/18-10:05
     * @Param: [openId]
     * @return: com.galen.micro.mini.model.UserWechatWeb
     **/
    UserWechatWeb selectByOpenId(String openId);

    /**
     * @Author: Galen
     * @Description: 获取登陆密码
     * @Date: 2019/5/9-16:13
     * @Param: [userId]
     * @return: java.util.Map
     **/
    Map selectByUserId(Long userId);

    /**
     * @Author: Galen
     * @Description: 根据openId更新
     * @Date: 2019/5/10-17:23
     * @Param: [openid]
     * @return: int
     **/
    int updatePrimaryKeyByOpenId(UserWechatWeb record);
}