package com.kele.aggregation.ws.service;

import com.alibaba.fastjson.JSON;
import com.kele.aggregation.ws.constant.BizGlobalConstants;
import com.kele.aggregation.ws.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
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
        Map<String, WebSocketSession> webSocketSessionMap = sessionStorage.getWebSocketSession(messageDTO.getUserId());
        webSocketSessionMap.values().forEach(e -> handlePushMessage(e, messageDTO));
    }

    private void handlePushMessage(WebSocketSession webSocketSession, MessageDTO messageDTO) {
        if (webSocketSession != null && webSocketSession.isOpen()) {
            // 消息id+ pageId 組合加锁, 保证用户在同一个页面收到的消息不重复;
            String globalKey = messageDTO.getMessageId() + ":" + webSocketSession.getAttributes().get(BizGlobalConstants.WEBSOCKET_USER_PAGE_UUID);
            RLock lock = redissonClient.getLock(globalKey);
            try {
                boolean b = lock.tryLock(-1L, 3000L, TimeUnit.MILLISECONDS);
                if (b) {
                    log.info("获取锁 {}", globalKey);
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
