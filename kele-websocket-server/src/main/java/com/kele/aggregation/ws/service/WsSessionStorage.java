package com.kele.aggregation.ws.service;

import com.kele.aggregation.common.dto.KeleResult;
import com.kele.aggregation.ws.dto.MessageDTO;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class WsSessionStorage {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String applicationName;

    private static final Map<String, WebSocketSession> sessionCache = new ConcurrentHashMap<>(128);

    public WebSocketSession getWebSocketSession(String key) {
        return sessionCache.get(key);
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) throws IOException {
        // 保持客户端和服务端只有一个 websocket 链接;
        String key = Objects.requireNonNull(webSocketSession.getPrincipal()).getName();
        if (sessionCache.containsKey(key) && sessionCache.get(key).isOpen() && webSocketSession.isOpen()) {
            webSocketSession.close();
        } else {
            sessionCache.put(key, webSocketSession);
        }


    }

    public void removeWebSocketSession(WebSocketSession session) {
        sessionCache.remove(Objects.requireNonNull(session.getPrincipal()).getName());
    }


    public void tellOtherInstances(MessageDTO message) {
        Application application = eurekaClient.getApplication(applicationName);
        List<InstanceInfo> instances = application.getInstances();
        instances.forEach(e -> sendToOtherInstances(e, message));
    }

    private void sendToOtherInstances(InstanceInfo e, MessageDTO message) {
        if (e.getStatus() == InstanceInfo.InstanceStatus.UP) {
            // 发送消息;
            String host = "http://" + e.getIPAddr() + ":" + e.getPort();

            try {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<MessageDTO> httpEntity = new HttpEntity<>(message, httpHeaders);
                restTemplate.postForEntity(host, httpEntity, KeleResult.class);
            } catch (Exception exception) {
                log.warn("通知失败...", exception);
            }

        }

    }
}
