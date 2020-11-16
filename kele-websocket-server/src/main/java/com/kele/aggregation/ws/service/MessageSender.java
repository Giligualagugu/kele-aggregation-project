package com.kele.aggregation.ws.service;

import com.alibaba.fastjson.JSON;
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
        WebSocketSession webSocketSession = sessionStorage.getWebSocketSession(messageDTO.getUserId());
        if (webSocketSession != null && webSocketSession.isOpen()) {
            TextMessage textMessage = new TextMessage(JSON.toJSONString(messageDTO));
            webSocketSession.sendMessage(textMessage);
        }

    }

}
