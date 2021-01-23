package com.galen.micro.sys.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Assert;

/**
 * @Author: pengrui
 * @Date: 2019/3/11-13:58
 * @Description: 
**/
public class JsonUtils {
    
    /**
     * @Author: pengrui
     * @Description: bean转json字符串
     * @Date:  2019/3/11-13:58
     * @Param: [obj]
     * @return: java.lang.String
     **/
    public static String beanToJsonStr(Object obj){
        Assert.notNull(obj);
        return JSONObject.toJSONString(obj);
    }

    /**
     * @Author: pengrui
     * @Description: json字符串转指定bean
     * @Date:  2019/3/11-15:28
     * @Param: [jsonStr, clazz]
     * @return: T
     **/
    public static <T> T jsonStrToBean(String jsonStr,Class<T> clazz){
        Assert.notNull(jsonStr);
        Assert.notNull(clazz);
        return JSONObject.parseObject(jsonStr,clazz);
    }
}
