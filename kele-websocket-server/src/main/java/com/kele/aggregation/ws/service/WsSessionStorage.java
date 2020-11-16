package com.kele.aggregation.ws.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WsSessionStorage {

    public static Map<String, WebSocketSession> sessionCache = new ConcurrentHashMap<>(128);

    public WebSocketSession getWebSocketSession(String key) {
        return sessionCache.get(key);
    }

    public void setWebSocketSession(String key, WebSocketSession webSocketSession) {
        sessionCache.put(key, webSocketSession);
    }

    public void removeWebSocketSession(String key) {
        sessionCache.remove(key);
    }
}
