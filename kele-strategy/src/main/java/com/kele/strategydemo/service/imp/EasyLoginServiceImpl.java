package com.kele.strategydemo.service.imp;

import com.kele.strategydemo.dto.EasySignInDTO;
import com.kele.strategydemo.service.EasyLoginService;
import com.kele.strategydemo.service.SmsCodeValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EasyLoginServiceImpl implements EasyLoginService {

    @Autowired
    SmsCodeValidService smsCodeValidService;

    @Override
    public Object logIn(EasySignInDTO dto) {

        boolean flag = smsCodeValidService.validCode(dto);

        if (!flag) {
            smsCodeValidService.upgradeFailLogin(dto.getPhone());
        }

        return "登陆成功";
    }

}
