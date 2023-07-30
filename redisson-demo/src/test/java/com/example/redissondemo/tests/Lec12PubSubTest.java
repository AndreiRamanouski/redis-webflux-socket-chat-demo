package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import org.junit.jupiter.api.Test;
import org.redisson.api.RTopicReactive;
import org.redisson.client.codec.StringCodec;

public class Lec12PubSubTest extends BaseTest {


    //    publish slack-room hi
    @Test
    public void subscriberOne(){
        RTopicReactive topic = redissonReactiveClient.getTopic("slack-room", StringCodec.INSTANCE);
        topic.getMessages(String.class)
                .doOnError(System.out::println)
                .doOnNext(i-> System.out.println("Sub one received message: " + i))
                .subscribe();
        sleep(100000);
    }

    @Test
    public void subscriberTwo(){
        RTopicReactive topic = redissonReactiveClient.getTopic("slack-room", StringCodec.INSTANCE);
        topic.getMessages(String.class)
                .doOnError(System.out::println)
                .doOnNext(i-> System.out.println("Sub two received message: " + i))
                .subscribe();
        sleep(100000);
    }

}
