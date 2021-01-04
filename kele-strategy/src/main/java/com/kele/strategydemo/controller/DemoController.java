package com.kele.strategydemo.controller;

import com.kele.strategydemo.dto.EasySignInDTO;
import com.kele.strategydemo.service.SmsCodeValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    SmsCodeValidService smsCodeValidService;

    @PostMapping("/easylogin")
    public Object logind(@RequestBody EasySignInDTO dto) {

        smsCodeValidService.validCode(dto);

        return "ok";
    }
}
