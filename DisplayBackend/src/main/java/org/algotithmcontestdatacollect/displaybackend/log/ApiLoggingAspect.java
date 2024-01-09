package org.algotithmcontestdatacollect.displaybackend.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class ApiLoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(ApiLoggingAspect.class);

    @Before("execution(* org.algotithmcontestdatacollect.displaybackend.controllers.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();

        // 记录提交的参数
        logger.error("接口调用前记录日志...");
        logger.error("方法名: " + method.getName());
        logger.error("参数: " + Arrays.toString(args));
        logger.error("调用时间: " + new Date());
    }
}
