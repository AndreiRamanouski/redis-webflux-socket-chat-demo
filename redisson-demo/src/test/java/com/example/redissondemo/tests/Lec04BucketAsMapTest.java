package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04BucketAsMapTest extends BaseTest {

    //    user:1:name
    //    user:2:name
    //    user:3:name

    @Test
    public void bucketAsMap() {
        Mono<Void> mono = redissonReactiveClient.getBuckets(StringCodec.INSTANCE)
                .get("user:1:name", "user:2:name", "user:3:name").doOnNext(System.out::println).then();
        StepVerifier.create(mono).verifyComplete();
    }
}
