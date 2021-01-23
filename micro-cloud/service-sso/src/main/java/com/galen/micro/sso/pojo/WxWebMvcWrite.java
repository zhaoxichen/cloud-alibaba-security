package com.galen.micro.sso.pojo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Galen
 * @Date: 2019/3/28-9:31
 * @Description: 微信页面信息
 **/
public class WxWebMvcWrite {
    /**
     * @Author: Galen
     * @Description: 输出信息到页面
     * @Date: 2019/3/28-9:35
     * @Param: [response]
     * @return: void
     **/
    public void writeToWeb(HttpServletRequest req, HttpServletResponse response, Integer pageShow) throws IOException {

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "accept,x-requested-with,Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET");
        response.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin") != null ? req.getHeader("Origin") : "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String title;
        String state;
        if (1 == pageShow) {
            title = "登录";
            state = "success";
        } else if (2 == pageShow) {
            title = "登录";
            state = "fail";
        } else if (3 == pageShow) {
            title = "绑定";
            state = "success";
        } else {
            title = "绑定";
            state = "fail";
        }
        out.print("<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>登录结果</title>\n" +
                "</head>\n" +
                "<style>\n" +
                "    @font-face {\n" +
                "        font-family: \"iconfont\";\n" +
                "        src: url('data:application/x-font-woff2;charset=utf-8;base64,d09GMgABAAAAAAPcAAsAAAAACCwAAAOPAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHEIGVgCDHAqDZINUATYCJAMQCwoABCAFhG0HTBtKB8ieGjtT0NCWkZAsmOYTMAnEw39j3+6bmRXRJJpcTt9FLJokImkr4o1I6XsIxdvviFWiTeo0FYhVotBYYFYcyXZIPlllcDKV6EeEFXj7rdftf8dh7v9/P/8qrXFWIVL6K2zJUcKJO6ORPJB77+M10Pi1DCTwuv+5nN7qeRbOb2e5zLH16F6A0QQKbE+gQAIuoLyXmDSMXXkRtwnUG1bEY7+hrQ/4K4xFgbgtiwj4pyxKDTPU6qtLdhbxVAqpTff8b/DEf3/8Mhv+JFWZsfLgar0FVH3VfixwJI4k20Uk6XgusH1kzAAKcb3Uc5FXmJnhpV75jLdFQK1aia/a1/gx8R/K9qKpNf/yCEkmqhm1jWAeZTNfNZsk+GqQJL4xiwzxMr8WNXhG8hsYBscpyqKxsddaNVhHt6RyriEqAFugkgNR8gsRGQdXtDxwcv/hWoxyHJM5dsflTuOs04u2GV07XX8sYi5W39HZozVa/7H7yXqsdwMKPrQ2LUePl/fA+7foTil4qsT5yRyVch8a6Yu9pfh9+QbE1rH3nz/BY1PoihxF3JT4tHb1qlQCUPkKi6AC/7jd4Fnh9PvR9GzNVNRW1tPbGd3BhyZousunmKHGLfba7nliKfFM2zOEgTIhKYTFu5r/XOFc80zus+In1oBzsag78m2CUf6u7c0nVhmPHqYvPavTtcjQydJEpLnMyffy0vvtS3Qv7EV/d2a8NUZI+t4c1kdwkpXkmTJHd/t7zxoma6sjxxL3VaAA+F826dt+zsDHfOFvz7L4KTzsha3/axsut6si6vYo9N+gdlrwy6Stpf9W2pJWVLlhLUVW2BLNV8y/oVRCvWnnjxZo92sMXVfrWkKtrhiSGkOQ1RpBC2MGqjSYh2q1tqDetMb9DTowXqLUYco2gNDqBSTNXkPW6gdaGD+hSrf/UK01vKDemeg4scFYyIF7DCOOLdA8D2WBOoll54FBfQ8Wp+2IJeU+ewAzVY5Do86QzTdiJ2ZjrFFnRBPnBBJGHbAB3IftdgoVRq1Y4DqJc6VErydlD9IJ1AEC7WEwhMMsILN5kExAORG3MxeYe30PTDTNDmE1lIrgAIxRyaNDRjqGFmijxtmKci3jVTNEJhxH8DyCoRygBujC7GaGgpTyaVaYgNOReqQUJfSoHWmr0c2vczzhIahn3FciRY4SVerv0GmnRZCwYIs22amK/e9K1CmqkgwAAA==') format('woff2');\n" +
                "    }\n" +
                "\n" +
                "    .iconfont {\n" +
                "        font-family: \"iconfont\", serif !important;\n" +
                "        font-size: 16px;\n" +
                "        font-style: normal;\n" +
                "        -webkit-font-smoothing: antialiased;\n" +
                "        -moz-osx-font-smoothing: grayscale;\n" +
                "    }\n" +
                "\n" +
                "    .icon-roundcheck:before {\n" +
                "        content: \"\\e657\";\n" +
                "    }\n" +
                "\n" +
                "    .icon-roundclose:before {\n" +
                "        content: \"\\e659\";\n" +
                "    }\n" +
                "\n" +
                "    .icon-zhongshi:before {\n" +
                "        content: \"\\e7c7\";\n" +
                "    }\n" +
                "\n" +
                "    html, body {\n" +
                "        width: 100%;\n" +
                "        height: 100%;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "        display: flex;\n" +
                "        align-items: center;\n" +
                "        justify-content: center;\n" +
                "    }\n" +
                "\n" +
                "    .container {\n" +
                "        width: 100%;\n" +
                "        height: 100%;\n" +
                "        display: flex;\n" +
                "        flex-direction: column;\n" +
                "        align-items: center;\n" +
                "        justify-content: center;\n" +
                "        overflow: hidden;\n" +
                "    }\n" +
                "\n" +
                "    .icon {\n" +
                "        font-size: 120px;\n" +
                "    }\n" +
                "\n" +
                "    .text {\n" +
                "        font-size: 24px;\n" +
                "    }\n" +
                "\n" +
                "    .retry {\n" +
                "        margin-top: 20px;\n" +
                "        color: #2D8CF0;\n" +
                "        cursor: pointer;\n" +
                "    }\n" +
                "\n" +
                "    .retry:hover {\n" +
                "        text-decoration: underline\n" +
                "    }\n" +
                "\n" +
                "    .retry-icon {\n" +
                "        margin-right: 6px;\n" +
                "    }\n" +
                "</style>\n" +
                "<body>\n" +
                "<div class=\"container\" id=\"container\">\n" +
                "    <div class=\"icon iconfont\" id=\"icon\"></div>\n" +
                "    <div class=\"text\" id=\"text\"></div>\n" +
                "</div>\n" +
                "</body>\n" +
                "<script>\n" +
                "    let state = '" + state + "';" +
                "    let title = '" + title + "';" +
                "    let icon = document.getElementById('icon');\n" +
                "    let text = document.getElementById('text');\n" +
                "    if (state === 'success') {\n" +
                "        icon.style.color = '#00C34F';\n" +
                "        icon.classList.add('icon-roundcheck');\n" +
                "        text.innerText = title + '成功!';\n" +
                "        if (window && window.parent) window.parent.postMessage({type: 'success', href: window.location.href}, '*');\n" +
                "        if (window && window.opener) window.opener.postMessage({type: 'success', href: window.location.href}, '*');\n" +
                "    } else if (state === 'fail') {\n" +
                "        icon.style.color = '#ccc';\n" +
                "        icon.classList.add('icon-roundclose');\n" +
                "        text.innerText = title + '失败!';\n" +
                "        let container = document.getElementById('container');\n" +
                "        let retry = document.createElement('div');\n" +
                "        retry.innerHTML = '<div class=\"retry\" onclick=\"handleOnClick()\"><span class=\"retry-icon iconfont icon-zhongshi\"></span>重试</div>';\n" +
                "        container.appendChild(retry)\n" +
                "    }\n" +
                "\n" +
                "    function handleOnClick() {\n" +
                "        if (window && window.parent) window.parent.postMessage({type: 'fail', href: window.location.href}, '*');\n" +
                "        if (window && window.opener) window.opener.postMessage({type: 'fail', href: window.location.href}, '*');\n" +
                "    }\n" +
                "</script>\n" +
                "</html>\n");
        out.flush();
        out.close();
    }
}
