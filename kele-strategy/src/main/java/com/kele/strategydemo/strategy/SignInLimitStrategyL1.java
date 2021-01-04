package com.kele.strategydemo.strategy;

import com.kele.strategydemo.dto.SignInLimitResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * L1: 1小时内登陆错误 3次  限制10分钟禁止登陆
 */
@Service
public class SignInLimitStrategyL1 implements SignInLimitStrategy {

    @Autowired
    SignInLimitStrategyL2 signInLimitStrategyL2;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public SignInLimitResult checkLimited(String phone) {


        return null;
    }

    @Override
    public SignInLimitStrategy getHighLevelStrategy() {
        return signInLimitStrategyL2;
    }

}
