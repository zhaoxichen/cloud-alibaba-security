package com.galen.micro.sso.service.impl;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sso.mapper.UserSecurityMapper;
import com.galen.micro.sso.mapper.UserWechatMapper;
import com.galen.micro.sso.model.UserWechatWeb;
import com.galen.micro.sso.pojo.WxToken;
import com.galen.micro.sso.pojo.WxWebMvcWrite;
import com.galen.micro.sso.service.LoginService;
import com.galen.micro.sso.utils.BCryptUtil;
import com.galen.micro.sso.utils.WxCommonUtil;
import com.galen.security.pojo.SecuritySysRole;
import com.galen.security.pojo.SecuritySysUser;
import com.galen.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserSecurityMapper userSecurityMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserWechatMapper userWechatMapper;
    //app_id
    @Value("${wechat.config.app-id:}")
    private String APP_ID;
    //密钥
    @Value("${wechat.config.app-secret:}")
    private String APP_SECRET;

    @Override

    public AplResponse loginNormal(String username, String password, String code, HttpServletRequest request) {
        /**
         * @Author: Galen
         * @Description: 查询数据库，获取登录的用户信息
         **/
        SecuritySysUser securityUser = userSecurityMapper.loadUserByUsername(username);
        if (securityUser == null) {
            return ResponseUtils.build(802, "登录失败,账户名错误");
        }
        if (!securityUser.getEnabled()) {
            return ResponseUtils.build(803, "账户被锁定，请联系管理员!");
        }
        /**
         * @Author: Galen
         * @Description: 校验密码
         **/
        if (!BCryptUtil.matches(password, securityUser.getPassword())) {
            return ResponseUtils.build(801, "登录失败,账户密码错误");
        }
        SecuritySysUser securityUserFull = this.setLoginStorage(request, securityUser);
        /**
         * 返回登陆结果
         */
        return ResponseUtils.SUCCESS(securityUserFull);
    }

    @Override
    public AplResponse loginMobilePhone(String mobile, String password, String code, HttpServletRequest request) {
        /**
         * @Author: Galen
         * @Description: 查询数据库，获取登录的用户信息
         **/
        SecuritySysUser securityUser = userSecurityMapper.loadUserByMobilePhone(mobile);
        if (securityUser == null) {
            return ResponseUtils.build(802, "登录失败，手机号错误");
        }
        if (!securityUser.getEnabled()) {
            return ResponseUtils.build(803, "账户被锁定，请联系管理员!");
        }
        /**
         * @Author: Galen
         * @Description: 校验密码
         **/
        if (!BCryptUtil.matches(password, securityUser.getPassword())) {
            return ResponseUtils.build(801, "登录失败,账户密码错误");
        }
        SecuritySysUser securityUserFull = this.setLoginStorage(request, securityUser);
        /**
         * 返回登陆结果
         */
        return ResponseUtils.SUCCESS(securityUserFull);
    }

    @Override
    public AplResponse logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (null == token) {
            token = request.getRequestedSessionId();
        }
        redisTemplate.delete(token);
        return ResponseUtils.invalid();
    }

    @Override
    public void loginScanWechatCallback(String code, String state, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        /**
         * @Author: Galen
         * @Description: 通过code获取access_token
         **/
        WxToken wxToken = WxCommonUtil.getToken(code, APP_ID, APP_SECRET);
        if (null == wxToken) {
            //测试用
            if ("123456".equals(code)) {
                wxToken = new WxToken();
                wxToken.setOpenid(state);
            } else {
                return;
            }
        }
        /**
         * @Author: Galen
         * @Description: 查看是否授权过
         **/
        UserWechatWeb userWechat = userWechatMapper.selectByOpenId(wxToken.getOpenid());
        if (null == userWechat) {
            userWechat = WxCommonUtil.getUserInfo(wxToken);
            if (null != userWechat) {
                userWechat.setUserId(IdUtil.generateNumberId());
                userWechat.setUnionId(wxToken.getUnionid());
                userWechat.setOpenId(wxToken.getOpenid());
                userWechatMapper.insert(userWechat);
            }
            /**
             * 返回登陆结果
             */
            try {
                new WxWebMvcWrite().writeToWeb(request, response, 2);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        /**
         * @Author: Galen
         * @Description: 查询数据库，获取绑定用户的用户信息
         **/
        SecuritySysUser securityUser = userSecurityMapper.loadUserByUserId(userWechat.getUserId());
        Integer pageShow;
        if (securityUser == null) {
            pageShow = 2;
        } else if (!securityUser.getEnabled()) {
            pageShow = 2;
        } else {
            System.out.println("微信授权登陆成功！");
            this.setLoginStorage(request, securityUser);
            pageShow = 1;
        }
        /**
         * 返回登陆结果
         */
        try {
            new WxWebMvcWrite().writeToWeb(request, response, pageShow);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: Galen
     * @Description: 存储登陆信息
     * @Date: 2019/6/19-11:39
     * @Param: [request, securityUser]
     * @return: com.galen.micro.sso.pojo.SecuritySysUser
     **/
    private SecuritySysUser setLoginStorage(HttpServletRequest request, SecuritySysUser securityUser) {
        /**
         * @Author: Galen
         * @Description: 校验关联的用户基本信息是否完善
         **/
        String token = request.getParameter("token");
        if (null == token) {
            token = request.getHeader("token");//获取调用方的请求头
            if (null == token) {
                token = request.getRequestedSessionId();//获取老的sessionId
                if (null == token) {
                    token = request.getSession().getId();//生成一个sessionId
                }
            }
        }
        /**
         * 查询拥有的角色
         */
        List<SecuritySysRole> ownRoleList = userSecurityMapper.getRolesByUserId(securityUser.getId());
        securityUser.setRoles(ownRoleList);
        redisTemplate.opsForValue().set(token, securityUser, 30, TimeUnit.MINUTES);
        securityUser.setToken(token);
        return securityUser;
    }
}
