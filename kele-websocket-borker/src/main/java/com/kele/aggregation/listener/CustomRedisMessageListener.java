package com.kele.aggregation.listener;

import com.kele.aggregation.dto.MessageBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomRedisMessageListener {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void handleSubscribeTopicMessage(MessageBody messageBody) {
        log.info("收到异步消息请求, 发送....");

//        MessageBody messageBody = JSON.parseObject(msg, MessageBody.class);

        simpMessagingTemplate.convertAndSendToUser(messageBody.getTargetUser(), messageBody.getDestination(), messageBody);
    }

}
