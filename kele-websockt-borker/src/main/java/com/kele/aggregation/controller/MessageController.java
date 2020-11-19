package com.kele.aggregation.controller;

import com.kele.aggregation.constant.WsConstans;
import com.kele.aggregation.dto.MessageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 点对点发送消息，将消息发送到指定用户
     */
    @PostMapping("/test/push-to-user")
    public void sendUserMessage(@RequestBody MessageBody messageBody) {

        // 调用 STOMP 代理进行消息转发
        simpMessagingTemplate.convertAndSendToUser(messageBody.getTargetUser(), WsConstans.topic, messageBody);

//        simpMessagingTemplate.convertAndSend(WsConstans.topic, messageBody);

    }


}
