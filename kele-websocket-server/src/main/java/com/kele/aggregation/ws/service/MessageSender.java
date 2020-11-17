package com.kele.aggregation.ws.service;

import com.alibaba.fastjson.JSON;
import com.kele.aggregation.ws.dto.MessageDTO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class MessageSender {

    @Autowired
    WsSessionStorage sessionStorage;

    @Autowired
    RedissonClient redissonClient;

    public void sendToUserClient(MessageDTO messageDTO) throws IOException {
        WebSocketSession webSocketSession = sessionStorage.getWebSocketSession(messageDTO.getUserId());
        if (webSocketSession != null && webSocketSession.isOpen()) {
            RLock lock = redissonClient.getLock(messageDTO.getMessageId());
            try {
                /*
                存在一个情况, client 通过gateway 在 websocket-server多个实例上建立了链接, 因为是 key是 username or userId,
                所以会存在 两个或以上的实都能获取有效websocketsession, 导致重复推送消息, 这里加一个全局锁;
                */
                lock.lockInterruptibly(2000L, TimeUnit.MILLISECONDS);
                TextMessage textMessage = new TextMessage(JSON.toJSONString(messageDTO));
                webSocketSession.sendMessage(textMessage);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

    }

}
