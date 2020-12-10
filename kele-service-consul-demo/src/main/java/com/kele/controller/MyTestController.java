package com.kele.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class MyTestController {

    @Value("${kele:666a}")
    private String info;

    @GetMapping("/test/kele")
    public Object getKele() {

        return info;
    }
}
