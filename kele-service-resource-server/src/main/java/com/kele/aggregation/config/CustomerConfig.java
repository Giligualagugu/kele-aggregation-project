package com.kele.aggregation.config;

import com.kele.aggregation.common.aspect.RestControllerLogAspect;
import com.kele.aggregation.common.config.GlobalResponseWrapperConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
public class CustomerConfig {


    @RestControllerAdvice
    public static class ResponseWrapperConfig extends GlobalResponseWrapperConfig {
    }

    @Bean
    public RestControllerLogAspect restControllerLogAspect() {
        return new RestControllerLogAspect();
    }

}
