package com.galen.micro.user.pojo;

import com.galen.model.AplResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Galen
 * @Date: 2019/3/28-9:31
 * @Description: 页面信息
 **/
public class GalenWebMvcWrite {
    /**
     * @Author: Galen
     * @Description: 输出信息到页面
     * @Date: 2019/3/28-9:35
     * @Param: [response, respBean]
     * @return: void
     **/
    public void writeToWeb(HttpServletRequest req, HttpServletResponse response, AplResponse respBean) throws IOException {

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "accept,x-requested-with,Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET");
        response.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin")!=null?req.getHeader("Origin"):"*");
        response.setHeader("Access-Control-Max-Age", "3600");
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
