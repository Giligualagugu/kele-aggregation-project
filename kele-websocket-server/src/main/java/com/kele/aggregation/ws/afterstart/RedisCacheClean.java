package com.kele.aggregation.ws.afterstart;

import com.kele.aggregation.ws.constant.BizGlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class RedisCacheClean implements ApplicationRunner {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 项目重启后 需要清除 缓存所有用户的 websocketSessionId;
     * <p>
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Set<String> keys = stringRedisTemplate.keys(BizGlobalConstants.WEBSOCKET_SESSION_PREFIX + "*");
        log.info("项目重启后 需要清除 缓存所有用户的 websocketSessionId ={}", keys);
        stringRedisTemplate.delete(keys);


    }
}
