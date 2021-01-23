package com.galen.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具类
 *
 * @author Hugo
 */
public class StringUtil {


    /**
     * 生成UUID
     *
     * @return
     */
    public static String generateUuid() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

    public static String getValueWithoutNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 是否空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }


    /**
     * @Author: Galen
     * @Description: 校验字段不为为空
     * @Date: 2019/2/21-16:09
     * @Param: [str]
     * @return: boolean
     **/
    public static boolean notEmpty(String str) {
        if (null == str) {
            return false;
        } else if ("".equals(str.trim())) {
            return false;
        } else if (" ".equals(str.trim())) {
            return false;
        } else if ("null".equals(str.trim())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否手机号码
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNO(String phone) {

        if (isEmpty(phone)) {
            return false;
        }

        Pattern p = Pattern
                .compile("^1[3|4|5|7|8|9][0-9]{9}$");
        Matcher m = p.matcher(phone);

        return m.matches();

    }

    //验证邮政编码
    public static boolean checkYZBM(String post) {
        if (post.matches("[1-9]\\d{5}(?!\\d)")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 是否身份证号码
     */
    public static boolean isIdCard(String idCard) {
        if (isEmpty(idCard)) {
            return false;
        }
        //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern p = Pattern.compile("^((\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z]))$");
        //通过Pattern获得Matcher
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    /**
     * 邮箱验证
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        Pattern p = Pattern
                .compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * qq号验证
     *
     * @param qq
     * @return
     */
    public static boolean isQQ(String qq) {
        if (isEmpty(qq)) {
            return false;
        }
        Pattern p = Pattern.compile("^[1-9][0-9]{4,}$");
        Matcher m = p.matcher(qq);

        return m.matches();
    }

    /**
     * @Author: Galen
     * @Description: 校验是带两位小数的数
     * @Date: 2019/3/13-22:36
     * @Param: [qq]
     * @return: boolean
     **/
    public static boolean isDecimalNum(String decimalNum) {
        if (null == decimalNum) {
            return false;
        }
        if (decimalNum.startsWith("-")) {
            System.out.println("负数");
            return false;
        }
        if ((decimalNum.length() - decimalNum.lastIndexOf(".")) > 3) {
            System.out.println("不是两位小数");
            return false;
        }
        try {
            Double.parseDouble(decimalNum);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("含有字母");
            return false;
        }
    }

    /**
     * @Author: Galen
     * @Description: 判断是 yyyy-MM-dd HH:mm:ss
     * @Date: 2019/3/17-23:22
     * @Param: [timeStr]
     * @return: boolean
     **/
    public static boolean isTimeStr(String timeStr) {
        if (null == timeStr) {
            return false;
        }
        return timeStr.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$");
    }

    /**
     * @Author: Galen
     * @Description: 判断是 HH:mm:ss
     * @Date: 2019/3/18-14:08
     * @Param: [clockStr]
     * @return: boolean
     **/
    public static boolean isClockStr(String clockStr) {
        if (null == clockStr) {
            return false;
        }
        return clockStr.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}$");
    }

    /**
     * @Author: Galen
     * @Description: 判断是 yyyy-MM-dd
     * @Date: 2019/3/17-23:35
     * @Param: [dayStr]
     * @return: boolean
     **/
    public static boolean isDayStr(String dayStr) {
        if (null == dayStr) {
            return false;
        }
        return dayStr.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    }

    /**
     * 读取指定路径文本信息
     *
     * @param filePath
     * @param encoding
     * @return
     */
    public static String readFile2Text(String filePath, String encoding) {
        if (filePath == null) {
            return null;
        }

        StringBuffer result = new StringBuffer();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    result.append(text);
                    result.append("\r\n");
                }
                read.close();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * @param strs
     * @return
     */
    public static boolean hasEmpty(String... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        }
        boolean result = false;
        for (String str : strs) {
            if (str == null || str.trim().equals("")) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static String getFileSuffix(String fileName) {
        if (isEmpty(fileName)) {
            return "";
        }
        return fileName.substring(fileName.indexOf("."));
    }

    /**
     * str转list
     *
     * @param str
     * @return
     */
    public static List<String> toList(String str) {
        List<String> list = new ArrayList<String>();
        if (isEmpty(str)) {
            return list;
        }
        str = str.substring(1, str.length() - 1);
        String[] subStr = str.split(",");
        for (int i = 0; i < subStr.length; i++) {
            list.add(subStr[i]);
        }
        System.out.println(list);
        System.out.println(list.size());
        return list;
    }

    public static List<String> toStringList(String str) {
        List<String> list = new ArrayList<String>();
        String[] subStr = str.split("\\]");
        for (int i = 0; i < subStr.length; i++) {
            list.add(subStr[i] + "]");
        }
        return list;
    }

    /**
     * @Author: Galen
     * @Description: 生成六位随机数
     * @Date: 2019/4/11-17:17
     * @Param: []
     * @return: java.lang.String
     **/
    public static String genRandomSix() {
        int result = (int) (Math.random() * (999999 - 100000) + 100000);
        return String.valueOf(result);
    }

    /**
     * @Author: Galen
     * @Description: 生成四位随机数
     * @Date: 2019/4/11-17:17
     * @Param: []
     * @return: java.lang.String
     **/
    public static String genRandomFour() {
        int result = (int) (Math.random() * (9999 - 1000) + 1000);
        return String.valueOf(result);
    }

}
