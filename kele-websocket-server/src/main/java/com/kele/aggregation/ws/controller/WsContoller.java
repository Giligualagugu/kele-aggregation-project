package com.kele.aggregation.ws.controller;

import com.kele.aggregation.common.dto.KeleResult;
import com.kele.aggregation.ws.dto.MessageDTO;
import com.kele.aggregation.ws.service.MessageSender;
import com.kele.aggregation.ws.service.WsSessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WsContoller {

    @Autowired
    WsSessionStorage sessionStorage;

    @Autowired
    MessageSender messageSender;

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
        messageSender.sendToUserClient(messageDTO);
        return KeleResult.success(null);
    }

}
