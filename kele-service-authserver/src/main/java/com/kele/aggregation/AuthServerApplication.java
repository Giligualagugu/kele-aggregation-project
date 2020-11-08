package com.kele.aggregation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author xujiale 2020/11/7 19:28
 *
 * 获取token请求
 * http://localhost:8004/oauth/token?username=demo&password=demo123&grant_type=password&scope=all&client_id=client_1&client_secret=123456
 */
@EnableAuthorizationServer
@EnableEurekaClient
@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
