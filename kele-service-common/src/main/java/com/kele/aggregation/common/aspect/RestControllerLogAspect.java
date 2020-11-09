package com.kele.aggregation.common.aspect;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.kele.aggregation.common.dto.KeleResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author xujiale
 * @date 2020/3/24 11:00
 * <p>
 * 作用于 所有controller入口;
 * <p>
 * 如需使用，请注册@Bean 到容器中；
 */
@Slf4j
@Aspect
public class RestControllerLogAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object aroundlog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String requestURI = request.getRequestURI();
        Object[] args = joinPoint.getArgs();
        String requestId = IdUtil.fastSimpleUUID();

        log.info("Request start ==> request-trace-id=[{}], request_uri=[{}], request_args=[{}]", requestId, requestURI, tryToFormatInputParams(args));

        long t1 = System.currentTimeMillis();

        Object proceed = joinPoint.proceed(args);
        String jsonResult = tryToFormatResponse(proceed);

        long t2 = System.currentTimeMillis();

        log.info("Request finish ==> response-trace-id=[{}], spent_time=[{}ms], response_body=[{}]", requestId, t2 - t1, jsonResult);

        return proceed;
    }

    private String tryToFormatResponse(Object proceed) {

        if (isSerializableForJson(proceed)) {
            final String jsonString = JSON.toJSONString(proceed);
            return jsonString.length() < 256 ? jsonString : "too long to print";
        }


        return "masked";

    }

    private Object tryToFormatInputParams(Object[] args) {
        return Arrays.toString(args);
    }

    private boolean isSerializableForJson(Object proceed) {

        if (proceed == null) {
            return false;
        }

        return proceed instanceof KeleResult || proceed instanceof CharSequence || proceed instanceof Map || proceed instanceof Collection || proceed.getClass().isArray();
    }


}
