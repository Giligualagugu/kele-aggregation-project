package com.kele.aggregation.ws.controller;

import com.kele.aggregation.common.dto.KeleResult;
import com.kele.aggregation.ws.dto.MessageDTO;
import com.kele.aggregation.ws.service.WebSocketMessageSender;
import com.kele.aggregation.ws.service.WsSessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class WsContoller {

    @Autowired
    WsSessionStorage sessionStorage;

    @Autowired
    WebSocketMessageSender webSocketMessageSender;

    /**
     * 推送信息到客户端;
     */
    @PostMapping("/ws/message")
    public KeleResult<Object> sendToUser(@RequestBody MessageDTO message) {
        sessionStorage.tellOtherInstances(message);
        return KeleResult.success(null);
    }


    /**
     * 内部接受广播...
     * <p>
     * 有可靠性MQ 可以用MQ替代;
     */
    @PostMapping("/inner/message")
    public KeleResult<Object> sendToUserClient(@RequestBody MessageDTO messageDTO) throws IOException {
        webSocketMessageSender.sendToUserClient(messageDTO);
        return KeleResult.success(null);
    }

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/test/redisson")
    public KeleResult<Object> testRedisson() {
        RLock lock = redissonClient.getLock("kelelockdistribute");
        boolean isLock = false;
        try {
            isLock = lock.tryLock();
            if (isLock) {
                log.info(" 做任务....");
                TimeUnit.MILLISECONDS.sleep(10_000L);
                return KeleResult.success("ok");
            } else {
                log.info("锁失败...");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e2) {
            System.out.println("锁定失败");
        } finally {
            if (isLock) {
                log.info("释放锁");
//                lock.unlock();
            }
        }
        return KeleResult.fail();
    }

    @GetMapping("/test/redisson2")
    public KeleResult<Object> testRedisson2() {
        RLock lock = redissonClient.getLock("kelelockdistribute");

        try {
            lock.lock(); // 未获取锁的线程会一直等待当前锁释放;
            log.info("上锁了....");
            TimeUnit.SECONDS.sleep(40L);
            return KeleResult.success("ok");
        } catch (Exception e) {
            System.out.println("加锁失败");
        } finally {
            lock.unlock();
        }


        return KeleResult.fail();
    }

}
