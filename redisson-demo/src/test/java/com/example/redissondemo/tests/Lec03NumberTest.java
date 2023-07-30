package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec03NumberTest extends BaseTest {


    @Test
    public void keyValueIncreaseTest(){
        RAtomicLongReactive atomicLong = redissonReactiveClient.getAtomicLong("user:1:visit");
        Mono<Void> then = Flux.range(1, 30).delayElements(Duration.ofSeconds(1))
                .flatMap(i -> atomicLong.incrementAndGet()).then();
        StepVerifier.create(then).verifyComplete();
    }
}
