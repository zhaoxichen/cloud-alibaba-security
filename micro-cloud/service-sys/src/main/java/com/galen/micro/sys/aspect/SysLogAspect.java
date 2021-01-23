package com.galen.micro.sys.aspect;

import com.galen.micro.sys.annotation.SysLog;
import com.galen.micro.sys.component.CurrentSecurityUser;
import com.galen.micro.sys.mapper.SysLogMapper;
import com.galen.micro.sys.model.SysLogEntity;
import com.galen.micro.sys.utils.HttpContextUtils;
import com.galen.micro.sys.utils.IPUtils;
import com.galen.utils.IdUtil;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author: Galen
 * @Date: 2019/6/28-12:03
 * @Description: 切面处理类
 **/
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private CurrentSecurityUser currentSecurityUser;
    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 监测自定义注解
     */
    @Pointcut("@annotation(com.galen.micro.sys.annotation.SysLog)")
    public void logPointCut() {

    }

    /**
     * 切面处理
     *
     * @Around 环绕
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLogEntity = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLogEntity.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = new Gson().toJson(args);
            sysLogEntity.setParams(params);
        } catch (Exception e) {

        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLogEntity.setIp(IPUtils.getIpAddr(request));

        //用户名
        String username = currentSecurityUser.getCurrentUser().getUsername();
        sysLogEntity.setUsername(username);

        sysLogEntity.setTime(time);
        sysLogEntity.setCreateDate(new Date());
        sysLogEntity.setId(IdUtil.generateNumberId());
        //保存系统日志
        sysLogMapper.insert(sysLogEntity);
    }

}
