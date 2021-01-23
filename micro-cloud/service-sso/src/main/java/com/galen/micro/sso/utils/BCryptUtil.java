package com.galen.micro.sso.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtil {

    /**
     * @Author: Galen
     * @Description: BCrypt加密
     * @Date: 2019/3/21-18:07
     * @Param: [psw]
     * @return: java.lang.String
     **/
    public final static String encode(String psw) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(psw);
    }

    /**
     * @Author: Galen
     * @Description: 比对密码
     * @Date: 2019/5/11-11:25
     * @Param: [rawPassword, encodedPassword]
     * @return: boolean
     **/
    public final static boolean matches(CharSequence rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }
}
