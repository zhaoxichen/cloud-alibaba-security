package com.galen.utils;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * <p>Http工具类
 *
 * <p>Http工具类，为系统提供通用Http访问操作方法：
 *
 * <p>1、发送GET请求；
 * <p>2、发送POST请求。
 */
public class HttpUtil {

    /**
     * <p>发送GET请求
     *
     * @param url GET请求地址
     * @return 与当前请求对应的响应内容字节数组
     */
    public static byte[] doGet(String url) {

        return HttpUtil.doGet(url, null, null, 0);
    }

    /**
     * <p>发送GET请求
     *
     * @param url       GET请求地址
     * @param headerMap GET请求头参数容器
     * @return 与当前请求对应的响应内容字节数组
     */
    public static byte[] doGet(String url, Map headerMap) {

        return HttpUtil.doGet(url, headerMap, null, 0);
    }

    /**
     * <p>发送GET请求
     *
     * @param url       GET请求地址
     * @param proxyUrl  代理服务器地址
     * @param proxyPort 代理服务器端口号
     * @return 与当前请求对应的响应内容字节数组
     * @modify 窦海宁, 2012-03-19
     */
    public static byte[] doGet(String url, String proxyUrl, int proxyPort) {

        return HttpUtil.doGet(url, null, proxyUrl, proxyPort);
    }

    /**
     * <p>发送GET请求
     *
     * @param url       GET请求地址
     * @param headerMap GET请求头参数容器
     * @param proxyUrl  代理服务器地址
     * @param proxyPort 代理服务器端口号
     * @return 与当前请求对应的响应内容字节数组
     * @modify 窦海宁, 2012-03-19
     */
    public static byte[] doGet(String url, Map headerMap, String proxyUrl, int proxyPort) {

        byte[] content = null;
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);

        if (headerMap != null) {

            //头部请求信息
            if (headerMap != null) {

                Iterator iterator = headerMap.entrySet().iterator();
                while (iterator.hasNext()) {

                    Entry entry = (Entry) iterator.next();
                    getMethod.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }

        if (StringUtils.isNotBlank(proxyUrl)) {

            httpClient.getHostConfiguration().setProxy(proxyUrl, proxyPort);
        }

        //设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
        //postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new DefaultHttpMethodRetryHandler());
        InputStream inputStream = null;
        try {

            if (httpClient.executeMethod(getMethod) == HttpStatus.SC_OK) {

                //读取内容
                inputStream = getMethod.getResponseBodyAsStream();
                content = IOUtils.toByteArray(inputStream);
            } else {

                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        } finally {

            IOUtils.closeQuietly(inputStream);
            getMethod.releaseConnection();
        }
        return content;
    }

    /**
     * <p>发送POST请求
     *
     * @param url          POST请求地址
     * @param parameterMap POST请求参数容器
     * @return 与当前请求对应的响应内容字节数组
     */
    public static byte[] doPost(String url, Map parameterMap) {

        return HttpUtil.doPost(url, null, parameterMap, null, null, 0);
    }

    /**
     * <p>发送POST请求
     *
     * @param url          POST请求地址
     * @param parameterMap POST请求参数容器
     * @param paramCharset 参数字符集名称
     * @return 与当前请求对应的响应内容字节数组
     * @modify 窦海宁, 2012-05-21
     */
    public static byte[] doPost(String url, Map parameterMap, String paramCharset) {

        return HttpUtil.doPost(url, null, parameterMap, paramCharset, null, 0);
    }

    /**
     * <p>发送POST请求
     *
     * @param url          POST请求地址
     * @param headerMap    POST请求头参数容器
     * @param parameterMap POST请求参数容器
     * @param paramCharset 参数字符集名称
     * @return 与当前请求对应的响应内容字节数组
     * @modify 窦海宁, 2012-05-21
     */
    public static byte[] doPost(String url, Map headerMap, Map parameterMap, String paramCharset) {

        return HttpUtil.doPost(url, headerMap, parameterMap, paramCharset, null, 0);
    }

    /**
     * <p>发送POST请求
     *
     * @param url          POST请求地址
     * @param parameterMap POST请求参数容器
     * @param paramCharset 参数字符集名称
     * @param proxyUrl     代理服务器地址
     * @param proxyPort    代理服务器端口号
     * @return 与当前请求对应的响应内容字节数组
     */
    public static byte[] doPost(String url, Map parameterMap, String paramCharset, String proxyUrl, int proxyPort) {

        return HttpUtil.doPost(url, null, parameterMap, paramCharset, proxyUrl, proxyPort);
    }

    /**
     * <p>发送POST请求
     *
     * @param url          POST请求地址
     * @param headerMap    POST请求头参数容器
     * @param parameterMap POST请求参数容器
     * @param paramCharset 参数字符集名称
     * @param proxyUrl     代理服务器地址
     * @param proxyPort    代理服务器端口号
     * @return 与当前请求对应的响应内容字节数组
     * @modify 窦海宁, 2012-05-21
     */
    public static byte[] doPost(String url, Map headerMap, Map parameterMap, String paramCharset, String proxyUrl, int proxyPort) {

        byte[] content = null;
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        if (StringUtils.isNotBlank(paramCharset)) {

            postMethod.getParams().setContentCharset(paramCharset);
            postMethod.getParams().setHttpElementCharset(paramCharset);
        }

        if (headerMap != null) {

            //头部请求信息
            if (headerMap != null) {

                Iterator iterator = headerMap.entrySet().iterator();
                while (iterator.hasNext()) {

                    Entry entry = (Entry) iterator.next();
                    postMethod.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }

        Iterator iterator = parameterMap.keySet().iterator();
        while (iterator.hasNext()) {

            String key = (String) iterator.next();
            postMethod.addParameter(key, (String) parameterMap.get(key));
        }

        if (StringUtils.isNotBlank(proxyUrl)) {

            httpClient.getHostConfiguration().setProxy(proxyUrl, proxyPort);
        }

        //设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
        //postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new DefaultHttpMethodRetryHandler());
        InputStream inputStream = null;
        try {

            if (httpClient.executeMethod(postMethod) == HttpStatus.SC_OK) {

                //读取内容
                inputStream = postMethod.getResponseBodyAsStream();
                content = IOUtils.toByteArray(inputStream);
            } else {

                System.err.println("Method failed: " + postMethod.getStatusLine());
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        } finally {

            IOUtils.closeQuietly(inputStream);
            postMethod.releaseConnection();
        }
        return content;
    }

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据<String形式传入>
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequestByJson(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
