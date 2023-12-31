package com.redis.caching.chat.service;

import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ChatRoomService implements WebSocketHandler {

    @Autowired
    private RedissonReactiveClient redissonReactiveClient;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String room = getChatRoomName(webSocketSession);
        String name = getName(webSocketSession);

        System.out.println("Chat room: " + room + " name " + name);
        RTopicReactive topic = redissonReactiveClient.getTopic(room, StringCodec.INSTANCE);

        RListReactive<String> historyList = redissonReactiveClient.getList("history:" + room, StringCodec.INSTANCE);

        webSocketSession.receive().map(WebSocketMessage::getPayloadAsText)
                .flatMap(msg -> historyList.add(msg).then(topic.publish(msg)))
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("Subscriber finally " + s))
                .subscribe();

        Flux<WebSocketMessage> messages = topic.getMessages(String.class)
                .startWith(historyList.iterator())
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("Publisher finally " + s))
                .map(webSocketSession::textMessage);

        return webSocketSession.send(messages);
    }

    private String getName(WebSocketSession webSocketSession) {
        URI uri = webSocketSession.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri).build().getQueryParams().toSingleValueMap().getOrDefault("name", "anonymous");
    }

    private String getChatRoomName(WebSocketSession webSocketSession) {
        URI uri = webSocketSession.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri).build().getQueryParams().toSingleValueMap().getOrDefault("room", "default");
    }
}
