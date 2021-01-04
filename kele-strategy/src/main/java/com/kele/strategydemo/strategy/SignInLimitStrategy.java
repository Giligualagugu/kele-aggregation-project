package com.kele.strategydemo.strategy;

import com.kele.strategydemo.dto.SignInLimitResult;

public interface SignInLimitStrategy {

    SignInLimitResult checkLimited(String phone);

    SignInLimitStrategy getHighLevelStrategy();
}
