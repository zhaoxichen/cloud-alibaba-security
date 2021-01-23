package com.galen.micro.zuul.api;

import com.galen.micro.zuul.api.feign.SsoFeign;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoxi on 2018/3/28
 */
@Component
public class ApiFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(ApiFilter.class);

    @Autowired
    private SsoFeign ssoFeign;

    @Value("${spring.profiles.active}")
    private String ACTIVE_EVN;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL()));
        String requestURI = request.getRequestURI();
        /**
         * @Author: Galen
         * @Description: 检查头信息
         **/
        String token = request.getHeader("token");
        if (null == token) {
            token = request.getRequestedSessionId();
            if (null == token) {
                token = request.getSession().getId();//生成一个sessionId
            }
            /**
             * @Author: Galen
             * @Description: 加入头信息后转发
             **/
            RequestContext context = RequestContext.getCurrentContext();
            context.addZuulRequestHeader("token", token);
        }
        /**
         * @Author: Galen
         * @Description: 开发环境使用swagger调试，需要处理一下
         **/
        if ("dev".equals(ACTIVE_EVN)) {
            requestURI = requestURI.replace("service-", "");
            log.info("开发环境使用swagger调试，处理后的uri>>>{}", requestURI);
        }
        /**
         * @Author: Galen
         * @Description: 匹配是否为免检uri
         **/
        if (configureIgnoring(requestURI)) {
            log.info("免检放行！");
            return null;
        }
        /**
         * @Author: Galen
         * @Description: 校验token
         **/
        if (null == requestURI || null == token) {
            log.warn("param error,requestUrl is {}", requestURI);
            setReturnCtx(ctx, "{\"status\":801,\"msg\":\"token is error\",\"total\":0,\"bean\":\"未登录!\"}");
        }
        int decideResult = ssoFeign.getDecide(requestURI, token);
        if (803 == decideResult) {
            log.warn("api zuul >>> token is limit");
            setReturnCtx(ctx, "{\"status\":803,\"msg\":\"token is error\",\"total\":0,\"bean\":\"被限制登陆，请联系管理员!\"}");
            return null;
        } else if (802 == decideResult) {
            log.warn("api zuul >>> token has no access root");
            setReturnCtx(ctx, "{\"status\":802,\"msg\":\"token is error\",\"total\":0,\"bean\":\"权限不足，请联系管理员!\"}");
            return null;
        } else if (801 == decideResult) {
            log.warn("api zuul >>> token isn't check");
            setReturnCtx(ctx, "{\"status\":801,\"msg\":\"token is error\",\"total\":0,\"bean\":\"未登录!\"}");
            return null;
        } else if (800 == decideResult) {
            log.warn("api zuul >>> system error");
            setReturnCtx(ctx, "{\"status\":800,\"msg\":\"token is error\",\"total\":0,\"bean\":\"单点登陆服务器异常!\"}");
            return null;
        }
        /**
         * @Author: Galen
         * @Description: 放行
         **/
        log.info("token is ok!");
        return null;
    }


    /**
     * @Author: Galen
     * @Description: 过滤器直接返回
     * @Date: 2019/7/1-11:52
     * @Param: [ctx, resultBody]
     * @return: void
     **/
    private void setReturnCtx(RequestContext ctx, String resultBody) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
        ctx.setResponseBody(resultBody); //返回json对象字符
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
    }

    /**
     * @Author: Galen
     * @Description: 配置放行的资源
     * @Date: 2019/6/29-10:48
     * @Param: [requestURI]
     * @return: boolean
     **/
    public boolean configureIgnoring(String requestURI) {
        final String[] uriIgnoringArr = {".+/v2/api-docs",
                ".+sso/login/scan/wechat/get", ".+sso/wechat/binding/callback",
                ".+sso/login", ".+sso/login/mobile", ".+sso/login/scan/wechat/callback", ".+sso/login/scan/qq/callback"};
        for (int i = 0; i < uriIgnoringArr.length; i++) {
            if (requestURI.matches(uriIgnoringArr[i])) {
                return true;
            }
        }
        return false;
    }
}
