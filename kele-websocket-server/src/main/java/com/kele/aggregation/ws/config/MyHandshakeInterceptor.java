package com.kele.aggregation.ws.config;

import com.kele.aggregation.ws.constant.BizGlobalConstants;
import com.kele.aggregation.ws.service.WsSessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {


    @Autowired
    WsSessionStorage sessionStorage;

    /**
     * 如果有已打开的链接...同一个页面的第二个链接不让进
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("握手之前....");
        String username = Objects.requireNonNull(serverHttpRequest.getPrincipal()).getName();
        ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
        String puuid = request.getServletRequest().getParameter("puuid");
        map.put(BizGlobalConstants.WEBSOCKET_USER_PAGE_UUID, puuid);

        if (StringUtils.isEmpty(puuid)) {
            return false;
        }

            /*
                当前实例 存在 该用户 pageId的 ws session 返回false;
             */
        Map<String, WebSocketSession> socketSession = sessionStorage.getWebSocketSession(username);

        if (ObjectUtils.isNotEmpty(socketSession)) {
            WebSocketSession webSocketSession = socketSession.get(puuid);
            if (webSocketSession != null) {
                log.warn("当前page 已存在 websocket链接");
                return false;
            }
        }

        return true;
    }


    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        // 握手流程, 首次握手
        log.info("握手结束....");
    }
}
