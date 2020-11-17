package com.kele.aggregation.ws.service;

import com.alibaba.fastjson.JSON;
import com.kele.aggregation.ws.constant.BizGlobalConstants;
import com.kele.aggregation.ws.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class WebSocketMessageSender {

    @Autowired
    WsSessionStorage sessionStorage;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void sendToUserClient(MessageDTO messageDTO) {

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(BizGlobalConstants.WEBSOCKET_SESSION_PREFIX + messageDTO.getUserId());

        // 获取到期实例保持的 该用户所有的 sessionId();

        for (Object value : entries.values()) {
            if (value instanceof String) {
                String sessionId = (String) value;
                CompletableFuture.runAsync(() -> {
                    WebSocketSession webSocketSession = sessionStorage.getWebSocketSession(sessionId);
                    if (webSocketSession != null && webSocketSession.isOpen()) {
                        TextMessage textMessage = new TextMessage(JSON.toJSONString(messageDTO));
                        try {
                            webSocketSession.sendMessage(textMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        log.info("发送完成...");
                    }
                });
            }
        }


    }

}
