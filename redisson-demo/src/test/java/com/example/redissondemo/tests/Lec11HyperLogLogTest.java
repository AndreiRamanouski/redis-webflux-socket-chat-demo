package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.redisson.api.RHyperLogLogReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec11HyperLogLogTest extends BaseTest {


    @Test
    public void count(){
        RHyperLogLogReactive<Long> counter = redissonReactiveClient.getHyperLogLog("user:visits",
                LongCodec.INSTANCE);

        List<Long> longs1 = LongStream.rangeClosed(1, 25000).boxed().toList();
        List<Long> longs2 = LongStream.rangeClosed(2501, 50000).boxed().toList();
        List<Long> longs3 = LongStream.rangeClosed(1, 75000).boxed().toList();
        List<Long> longs4 = LongStream.rangeClosed(60000, 100000).boxed().toList();

        Mono<Void> then = Flux.just(longs1, longs2, longs3, longs4).flatMap(counter::addAll).then();
        StepVerifier.create(then).verifyComplete();

        counter.count().doOnNext(System.out::println).subscribe();

    }

}
