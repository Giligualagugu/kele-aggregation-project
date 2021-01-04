package com.kele.strategydemo.service.imp;

import com.kele.strategydemo.dto.EasySignInDTO;
import com.kele.strategydemo.service.SmsCodeValidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.kele.strategydemo.constant.BizConstant.LOGIN_FAILED_COUNT;

@Slf4j
@Service
public class SmsCodeValidServiceImpl implements SmsCodeValidService {


    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean validCode(EasySignInDTO dto) {
        log.info("模拟登陆失败:{}", dto);
        return false;
    }

    /**
     * 登陆失败 次数+1
     */
    @Override
    public void upgradeFailLogin(String phone) {
        String key = LOGIN_FAILED_COUNT + phone;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForValue().increment(key, 1L);
        } else {
            redisTemplate.opsForValue().set(key, 1, 1L, TimeUnit.HOURS);
        }
    }
}
