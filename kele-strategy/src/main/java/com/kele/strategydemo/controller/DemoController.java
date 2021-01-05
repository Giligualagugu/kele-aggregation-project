package com.kele.strategydemo.controller;

import com.kele.strategydemo.dto.EasySignInDTO;
import com.kele.strategydemo.service.EasyLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    EasyLoginService easyLoginService;

    /**
     * 1.判断是否允许登陆
     * 2.登陆失败 plus失败次数
     */
    @PostMapping("/easylogin")
    public Object logind(@RequestBody EasySignInDTO dto) {

        Object reuslt = easyLoginService.logIn(dto);

        return reuslt;
    }
}
