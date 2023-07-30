package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import java.time.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBlockingQueueReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec10MessageQueueTest extends BaseTest {

    private RBlockingQueueReactive<Long> messageQueue;

    @BeforeAll
    public void setupQueue() {
        messageQueue = redissonReactiveClient.getBlockingQueue("message-queue", LongCodec.INSTANCE);
    }

    @Test
    public void consumerOne() {
        messageQueue.takeElements().doOnNext(i -> System.out.println("Consumer One received message => " + i))
                .doOnError(System.out::println).subscribe();
        sleep(600000);
    }

    @Test
    public void consumerTwo() {
        messageQueue.takeElements().doOnNext(i -> System.out.println("Consumer Two received message => " + i))
                .doOnError(System.out::println).subscribe();
        sleep(600000);
    }

    @Test
    public void producer() {
        Mono<Void> then = Flux.range(1, 100).delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Pushing message: " + i)).map(Long::valueOf)
                .flatMap(messageQueue::add).then();
        StepVerifier.create(then).verifyComplete();
    }

}
