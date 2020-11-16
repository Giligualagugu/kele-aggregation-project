package com.kele.aggregation.ws.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurationSupport;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig extends WebSocketConfigurationSupport {

    @Autowired
    MyHandshakeHandler handshakeHandler;

    @Autowired
    MyHandshakeInterceptor handshakeInterceptor;

    @Autowired
    CustomTextWebSocketHandler customTextWebSocketHandler;

    /**
     * ws端点, 可以配置多个 自由组合;
     */
    @Override
    protected void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customTextWebSocketHandler, "/websocketserver")
                .setAllowedOrigins("*")
                .setHandshakeHandler(handshakeHandler)
                .addInterceptors(handshakeInterceptor);
//                .withSockJS();

    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


}
