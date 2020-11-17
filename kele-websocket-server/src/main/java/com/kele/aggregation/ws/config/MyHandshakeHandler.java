package com.kele.aggregation.ws.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler {

    /**
     * 从这里获取 userName 然后 构建 Principal 对象;
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        log.info("这里构建 Principal ....");
        log.info("attributes={}", attributes);
        Principal principal = request.getPrincipal();
        return super.determineUser(request, wsHandler, attributes);
    }
}
