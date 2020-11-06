package com.kele.aggregation.common.config;

import com.kele.aggregation.common.dto.KeleResult;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseWrapperConfig implements ResponseBodyAdvice<Object> {

    private static final Class<ResponseBody> CLASS_TYPE = ResponseBody.class;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), CLASS_TYPE)
                || methodParameter.hasMethodAnnotation(CLASS_TYPE);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {


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
        return KeleResult.success(o);
    }
}
