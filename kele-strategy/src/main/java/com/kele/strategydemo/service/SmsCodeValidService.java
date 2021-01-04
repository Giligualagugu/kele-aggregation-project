package com.kele.strategydemo.service;

import com.kele.strategydemo.dto.EasySignInDTO;

public interface SmsCodeValidService {
    boolean validCode(EasySignInDTO dto);

    void upgradeFailLogin(String phone);
}
