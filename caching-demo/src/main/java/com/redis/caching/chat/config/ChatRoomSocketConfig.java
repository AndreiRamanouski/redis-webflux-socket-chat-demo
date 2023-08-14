package com.redis.caching.chat.config;

import com.redis.caching.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;

@Configuration
public class ChatRoomSocketConfig {

    @Autowired
    private ChatRoomService chatRoomService;

    @Bean
    public HandlerMapping handlerMapping(){
        Map<String, WebSocketHandler> stringChatRoomServiceMap = Map.of("/chat", chatRoomService);
        // set higher priority over other controllers
        return new SimpleUrlHandlerMapping(stringChatRoomServiceMap, -1);
    }

}
