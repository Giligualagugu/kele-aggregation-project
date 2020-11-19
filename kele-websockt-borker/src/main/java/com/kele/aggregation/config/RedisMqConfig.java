package com.kele.aggregation.config;

import com.kele.aggregation.listener.CustomRedisMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import static com.kele.aggregation.constant.WsConstans.REDIS_TOPIC_SERVER_PUSH_TO_WEB;

@Configuration
public class RedisMqConfig {

    @Autowired
    CustomRedisMessageListener customRedisMessageListener;


    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(customRedisMessageListener, "handleSubscribeTopicMessage");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(messageListenerAdapter(), REDIS_TOPIC_SERVER_PUSH_TO_WEB);
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }
}
