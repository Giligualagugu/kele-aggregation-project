package com.kele.aggregation.constant;

import org.springframework.data.redis.listener.ChannelTopic;

public final class WsConstans {

    /**
     * stomp 订阅主题
     */
    public static final String TOPIC = "/topic/kelepush";

    public static final ChannelTopic REDIS_TOPIC_SERVER_PUSH_TO_WEB = new ChannelTopic("redis_topic_server_push_to_web");
}
