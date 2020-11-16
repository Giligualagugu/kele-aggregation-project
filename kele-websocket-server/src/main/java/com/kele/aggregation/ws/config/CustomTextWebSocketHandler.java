package com.kele.aggregation.ws.config;

import com.kele.aggregation.ws.service.WsSessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CustomTextWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    WsSessionStorage sessionStorage;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info("链接关闭...{},{}", status.getCode(), status.getReason());
        sessionStorage.removeWebSocketSession(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("链接建立...");
        sessionStorage.setWebSocketSession( session);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("处理信息...{}", message.getPayload());

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.warn("传输过程错误....{}", exception.getMessage());
        if (session.isOpen()) session.close();
        sessionStorage.removeWebSocketSession(session);

    }
}
