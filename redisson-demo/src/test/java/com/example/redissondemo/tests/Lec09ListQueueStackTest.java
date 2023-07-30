package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.redisson.api.RDequeReactive;
import org.redisson.api.RListReactive;
import org.redisson.api.RQueueReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec09ListQueueStackTest extends BaseTest {


    @Test
    public void listTest() {

        RListReactive<Long> list = redissonReactiveClient.getList("number-input", LongCodec.INSTANCE);

        //in order
        List<Long> longs = LongStream.rangeClosed(1, 10).boxed().toList();
        StepVerifier.create(list.addAll(longs).then()).verifyComplete();
        StepVerifier.create(list.size()).expectNext(10).verifyComplete();

        //without order
        //        Mono<Void> longList = Flux.range(1, 10)
        //                .map(Long::valueOf)
        //                .flatMap(list::add)
        //                .then();
        //
        //        StepVerifier.create(longList).verifyComplete();
        //        StepVerifier.create(list.size()).expectNext(10).verifyComplete();
    }


    @Test
    public void queueTest() {
        RQueueReactive<Long> queue = redissonReactiveClient.getQueue("number-input", LongCodec.INSTANCE);
        Mono<Void> queuePoll = queue.poll().repeat(3).doOnNext(System.out::println).then();
        StepVerifier.create(queuePoll).verifyComplete();
        StepVerifier.create(queue.size()).expectNext(6).verifyComplete();

    }

    @Test
    public void stackTest() {
        // Deque
        RDequeReactive<Long> deque = redissonReactiveClient.getDeque("number-input", LongCodec.INSTANCE);
        Mono<Void> queuePoll = deque.pollLast().repeat(3).doOnNext(System.out::println).then();
        StepVerifier.create(queuePoll).verifyComplete();
        StepVerifier.create(deque.size()).expectNext(2).verifyComplete();

    }

}
