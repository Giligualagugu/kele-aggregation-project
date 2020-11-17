package com.kele.aggregation.ws.config;

import com.kele.aggregation.ws.constant.BizGlobalConstants;
import com.kele.aggregation.ws.service.WsSessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

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

        if (StringUtils.isEmpty(puuid)) {
            return false;
        }

        String existSessionId = sessionStorage.getStoreWebsocketSessionId(username, puuid);
        if (StringUtils.isNotEmpty(existSessionId)) {
            /*
                理论上 session关闭一定会触发 afterConnectionClosed();
                然后删除 缓存的 sessionId, 所以 sessionId 存在则 WebSocketSession 一定存在;
                但是WebSocketSession 不一定在当前实例中;  我们默认他是open的;

             */
            return false;
//            WebSocketSession webSocketSession = sessionStorage.getWebSocketSession(existSessionId);
//            if (webSocketSession != null && webSocketSession.isOpen()) {
//                return false;
//            }
        }

        map.put(BizGlobalConstants.WEBSOCKET_USER_PAGE_UUID, puuid);

        return true;
    }


    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        // 握手流程, 首次握手
        log.info("握手结束....");
    }
}
