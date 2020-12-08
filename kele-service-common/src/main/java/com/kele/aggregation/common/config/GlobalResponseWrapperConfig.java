package com.kele.aggregation.common.config;

import com.alibaba.fastjson.JSON;
import com.kele.aggregation.common.dto.KeleResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;


/**
 * 需自行注册处理 @RestControllerAdvice
 */
@Slf4j
public class GlobalResponseWrapperConfig implements ResponseBodyAdvice<Object> {

    private static final Class<ResponseBody> CLASS_TYPE = ResponseBody.class;

    public GlobalResponseWrapperConfig() {
        log.info("**************** 配置 ResponseBody 包装对象");
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), CLASS_TYPE)
                || methodParameter.hasMethodAnnotation(CLASS_TYPE);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (o == null) {
            return KeleResult.success(null);
        }

        // 处理 String 返回值;
        if (o instanceof String) {
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return JSON.toJSONString(KeleResult.success(o));
        }


        if (o instanceof KeleResult) {
            // 这里可以防止二次包装

            /*  !!!  feign 声明式rpc调用http接口获取的 ResponseBody 首先被解析成json格式的字符串,是没有class 信息的;
                因此如果 在 feignclient 接口里 使用 Object对象去接,比如 [ public Ojbect getRpcResult(); ]
                则框架将会默认转换成 LinkedHashMap 类型; 无法进入此逻辑, 依然会造成二次包装问题;
                所以, @FeignClient 修饰的接口里  所有的方法返回类型必须"显示声明" 为 KeleResult;
             */

            /* TODO 若涉及第三方 回调我方系统接口, 要求其他的封装格式, 需要再这里添加新的 instance of 判断条件;
             *
             */

            return o;
        }

        // 接上文, 如果不强制转换 可以在这里判断下 LinkedHashMap的外层结构和keleResult是不是一致, 一致则直接返回,否则包装进新的 keleresult
        if (o instanceof LinkedHashMap) {
            LinkedHashMap map = (LinkedHashMap) o;
            if (map.containsKey("code") && map.containsKey("message") && map.containsKey("result")) {
                return map;
            } else {
                return KeleResult.success(map);
            }

        }


        return KeleResult.success(o);
    }
}
