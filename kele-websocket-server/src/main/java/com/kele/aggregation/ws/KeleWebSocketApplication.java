package com.kele.aggregation.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
public class KeleWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeleWebSocketApplication.class, args);
    }

}
