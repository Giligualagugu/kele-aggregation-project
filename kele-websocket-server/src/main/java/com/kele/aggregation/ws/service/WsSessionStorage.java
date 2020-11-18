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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    private static final Map<String, WebSocketSession> sessionCache = new ConcurrentHashMap<>(128);

    public WebSocketSession getWebSocketSession(Object key) {
        return sessionCache.get(key);
    }

    /**
     * 很沙雕的问题... 第二个WebSocketSession 进来后, 找到 第一个WebSocketSession 调用close()方法尝试关闭... 结果把第二个WebSocketSession 也给关了...
     * <p>
     * 所以改变策略, 存在打开的WebSocketSession, 不让第二个WebSocketSession建立
     */
    public void setWebSocketSession(WebSocketSession webSocketSession) throws IOException {
        // 链接建立后 移除同页面的上一个链接;
        String userId = webSocketSession.getPrincipal().getName();
        // 缓存新的链接;
        log.info("缓存新链接:{}", webSocketSession.getId());
        sessionCache.put(userId, webSocketSession);
    }


    public void removeWebSocketSession(WebSocketSession session) {

        if (session == null) {
            return;
        }
        String userId = session.getPrincipal().getName();
        sessionCache.remove(userId);

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
