package com.kele.aggregation.ws.service;

import com.kele.aggregation.common.dto.KeleResult;
import com.kele.aggregation.ws.constant.BizGlobalConstants;
import com.kele.aggregation.ws.dto.MessageDTO;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class WsSessionStorage {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    EurekaClient eurekaClient;


    @Value("${spring.application.name}")
    private String applicationName;


    private static final Map<String, Map<String, WebSocketSession>> sessionCache = new ConcurrentHashMap<>(256);


    public Map<String, WebSocketSession> getWebSocketSession(String userId) {
        return sessionCache.get(userId);
    }

    /**
     * 很沙雕的问题... 第二个WebSocketSession 进来后, 找到 第一个WebSocketSession 调用close()方法尝试关闭... 结果把第二个WebSocketSession 也给关了...
     * <p>
     * 所以改变策略, 存在打开的WebSocketSession, 不让第二个WebSocketSession建立
     */
    public void setWebSocketSession(WebSocketSession webSocketSession) {
        String userId = Objects.requireNonNull(webSocketSession.getPrincipal()).getName();
        String pageId = (String) webSocketSession.getAttributes().get(BizGlobalConstants.WEBSOCKET_USER_PAGE_UUID);
        // 缓存新的链接;
        Map<String, WebSocketSession> sessionMap = sessionCache.get(userId);
        if (ObjectUtils.isEmpty(sessionMap)) {
            sessionMap = new HashMap<>();
            sessionMap.put(pageId, webSocketSession);
            sessionCache.put(userId, sessionMap);
        } else {
            sessionMap.put(pageId, webSocketSession);
        }

    }


    public void removeWebSocketSession(WebSocketSession session) {

        if (session == null) {
            return;
        }
        String userId = Objects.requireNonNull(session.getPrincipal()).getName();
        String pageId = (String) session.getAttributes().get(BizGlobalConstants.WEBSOCKET_USER_PAGE_UUID);

        Map<String, WebSocketSession> sessionMap = sessionCache.get(userId);
        sessionMap.remove(pageId, session);

    }


    public void tellOtherInstances(MessageDTO message) {
        Application application = eurekaClient.getApplication(applicationName);
        List<InstanceInfo> instances = application.getInstances();
        instances.forEach(e -> sendToOtherInstances(e, message));
    }

    private void sendToOtherInstances(InstanceInfo e, MessageDTO message) {
        if (e.getStatus() == InstanceInfo.InstanceStatus.UP) {
            // 发送消息;
            CompletableFuture.runAsync(() -> {
                try {
                    String host = "http://" + e.getIPAddr() + ":" + e.getPort() + "/inner/message";
                    log.info("发送域名:{}", host);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<MessageDTO> httpEntity = new HttpEntity<>(message, httpHeaders);
                    ResponseEntity<KeleResult> keleResultResponseEntity = restTemplate.postForEntity(host, httpEntity, KeleResult.class);
                    log.info("通知结果:{}", keleResultResponseEntity.getStatusCodeValue());
                } catch (Exception exception) {
                    log.warn("通知失败...", exception);
                }
            });


        }

    }
}
