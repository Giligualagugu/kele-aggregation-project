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

    @PostMapping("/ws/message")
    public KeleResult<Object> sendToUser(@RequestBody MessageDTO message) {

        sessionStorage.tellOtherInstances(message);

        return KeleResult.success(null);
    }


    @PostMapping("/inner/message")
    public KeleResult<Object> sendToUserClient(@RequestBody MessageDTO messageDTO) throws IOException {

        messageSender.sendToUserClient(messageDTO);

        return KeleResult.success(null);
    }

}
