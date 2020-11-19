package com.kele.aggregation.controller;

import com.kele.aggregation.constant.WsConstans;
import com.kele.aggregation.dto.MessageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MessageController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    /**
     * 通过 websocket stomp协议发送消息到服务器, 这里可以更新消息到已读, 获取未推送消息, 获取历史消息记录等;
     */
    @MessageMapping("/read")
    public void setToRead(MessageBody messageBody, Principal principal) {
        System.out.println(principal.getName());
        System.out.println("服务器收到消息:" + messageBody);
    }

    /**
     * 点对点发送消息，将消息发送到指定用户
     */
    @PostMapping("/test/push-to-user")
    public void sendUserMessage(@RequestBody MessageBody messageBody) {

        // 调用 STOMP 代理进行消息转发
        simpMessagingTemplate.convertAndSendToUser(messageBody.getTargetUser(), WsConstans.topic, messageBody);

//        simpMessagingTemplate.convertAndSend(WsConstans.topic, messageBody);

    }

    /**
     * 广播到指定的 订阅主题;
     */
    @PostMapping("/test/broadcast")
    public void broadcastMessageToDestination(@RequestBody MessageBody messageBody) {

        simpMessagingTemplate.convertAndSend(WsConstans.topic, messageBody);

    }


}
