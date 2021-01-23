package com.galen.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AplUtil {


    /**
     * @Author: Galen
     * @Description: 获取项目路径
     * @Date: 2019/3/16-10:39
     * @Param: [fileStr]
     * @return: java.lang.String
     **/
    public static String getRootFile() {
        return new File("").getAbsolutePath();
    }

    /**
     * @Author: Galen
     * @Description: 产生一个32位的订单号
     * @Date: 2019/4/17-10:15
     * @Param: []
     * @return: java.lang.String
     **/
    public static String genOrdersNo() {
        StringBuilder serialNo = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        serialNo.append(simpleDateFormat.format(new Date()));
        //15位随机数
        for (int i = 0; i < 15; i++) {
            serialNo.append((int) (Math.random() * 9));
        }
        return serialNo.toString();
    }

    /**
     * @Author: Galen
     * @Description: 返回16位数字
     * @Date: 2019/4/23-14:20
     * @Param: []
     * @return: java.lang.String
     **/
    public static String genNumberSixteenStr() {
        StringBuilder serialNo = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssSS");
        serialNo.append(simpleDateFormat.format(new Date()));
        //2位随机数
        serialNo.append((int) (Math.random() * 90 + 10));
        return serialNo.toString();
    }

}
