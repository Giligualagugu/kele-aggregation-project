package com.kele.aggregation.controller;

import com.kele.aggregation.rpc.RpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    RpcClient rpcClient;

    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("jack", 8789);
        return map;
    }

    @GetMapping("/rpc")
    public Object getRpc() {
        return rpcClient.getRpcResult();
    }
}
