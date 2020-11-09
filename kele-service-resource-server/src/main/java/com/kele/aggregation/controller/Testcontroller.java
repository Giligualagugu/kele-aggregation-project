package com.kele.aggregation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {

    @GetMapping("/test")
    public String test() {
        return "just a test";
    }

    @GetMapping("/protect")
    public String protect() {
        return "i am being protected";
    }
}
