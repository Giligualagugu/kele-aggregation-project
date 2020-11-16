package com.kele.aggregation.ws.service;

import com.kele.aggregation.ws.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Lazy
@Service
public class MessageSender {

    @Autowired
    WsSessionStorage sessionStorage;

    @Async
    public void sendToUserClient(MessageDTO messageDTO) throws IOException {
        String key = getSessionIdByUser(messageDTO.getUser());

        WebSocketSession webSocketSession = sessionStorage.getWebSocketSession(key);

        TextMessage textMessage = new TextMessage(messageDTO.getMessage());

        webSocketSession.sendMessage(textMessage);

    }

    private String getSessionIdByUser(String user) {
        // todo 处理 user和 sessionId 的缓存关系; 使用全局存储, mysql 或者 redis等;

        return null;
    }
}
