package com.kele.aggregation.ws.service;

import com.alibaba.fastjson.JSON;
import com.kele.aggregation.ws.constant.BizGlobalConstants;
import com.kele.aggregation.ws.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WebSocketMessageSender {

    @Autowired
    WsSessionStorage sessionStorage;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    public void sendToUserClient(MessageDTO messageDTO) {

//        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(BizGlobalConstants.WEBSOCKET_SESSION_PREFIX + messageDTO.getUserId());
        Set<String> keys = stringRedisTemplate.keys(BizGlobalConstants.WEBSOCKET_SESSION_PREFIX + messageDTO.getUserId() + "*");

        if (ObjectUtils.isEmpty(keys)) {
            return;
        }
        Set<String> wsSeesionId = new HashSet<>();
        keys.forEach(key -> {
            Set<String> members = stringRedisTemplate.opsForSet().members(key);
            if (ObjectUtils.isNotEmpty(members)) {
                wsSeesionId.addAll(members);
            }
        });

        wsSeesionId.forEach(sessionId -> handleMessageToUser(messageDTO, sessionId));


    }

    private void handleMessageToUser(MessageDTO messageDTO, String sessionId) {
        WebSocketSession webSocketSession = sessionStorage.getWebSocketSession(sessionId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            RLock lock = redissonClient.getLock(messageDTO.getMessageId() + webSocketSession.getAttributes().get(BizGlobalConstants.WEBSOCKET_USER_PAGE_UUID));

            try {
                boolean b = lock.tryLock(-1L, 3000L, TimeUnit.MILLISECONDS);
                if (b) {
                    log.info("获取锁 {}", messageDTO.getMessageId() + webSocketSession.getAttributes().get(BizGlobalConstants.WEBSOCKET_USER_PAGE_UUID));
                    TextMessage textMessage = new TextMessage(JSON.toJSONString(messageDTO));
                    webSocketSession.sendMessage(textMessage);
                    log.info("发送完成...");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // 这里finally 不能主动释放锁, 等待自动释放即可, 提前主动释放 可能让其他实例的线程拿到锁,然后重复执行;
        }
    }

}
