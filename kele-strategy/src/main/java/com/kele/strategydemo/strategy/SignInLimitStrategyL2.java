package com.kele.strategydemo.strategy;

import com.kele.strategydemo.dto.SignInLimitResult;
import org.springframework.stereotype.Service;

/**
 * L2: 1小时内登陆失败5次, 限制24小时内禁止登陆;
 */
@Service
public class SignInLimitStrategyL2 implements SignInLimitStrategy {

    @Override
    public SignInLimitResult checkLimited(String phone) {

        return null;
    }

    @Override
    public SignInLimitStrategy getHighLevelStrategy() {
        return null;
    }

}
