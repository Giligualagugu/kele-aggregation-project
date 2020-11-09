package com.kele.aggregation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {


    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("tom", 12312);
        return map;
    }

    @GetMapping("/rpc")
    public Object getRpc() {
        return test();
    }


    @GetMapping("/inner/test")
    public String testInner() {
        return "heheda  卧槽";
    }
}
