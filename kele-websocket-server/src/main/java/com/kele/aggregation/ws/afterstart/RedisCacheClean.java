package com.kele.aggregation.ws.afterstart;

import com.kele.aggregation.ws.constant.BizGlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisCacheClean implements ApplicationRunner {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 项目重启后 需要清除 缓存所有用户的 websocketSessionId;
     * <p>
     * 分布式service 如果分批次启动  后续批次启动时会 使前面批次的 实例已经建立的 sessionId缓存清空;
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Set<String> keys = stringRedisTemplate.keys(BizGlobalConstants.WEBSOCKET_SESSION_PREFIX + "*");

        stringRedisTemplate.delete(keys);


    }
}
