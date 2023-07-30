package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01KeyValueTest extends BaseTest {


    @Test
    public void keyValueAccessTest(){
        RBucketReactive<Object> bucket = redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam");
        Mono<Void> get = bucket.get().doOnNext(System.out::println).then();
        StepVerifier.create(set.concatWith(get)).verifyComplete();
    }

    @Test
    public void keyValueExpiryTest(){
        RBucketReactive<Object> bucket = redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", Duration.ofSeconds(10));
        Mono<Void> get = bucket.get().doOnNext(System.out::println).then();
        StepVerifier.create(set.concatWith(get)).verifyComplete();
    }

    @Test
    public void keyValueExtendExpiryTest(){
        RBucketReactive<Object> bucket = redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", Duration.ofSeconds(5));
        Mono<Void> get = bucket.get().doOnNext(System.out::println).then();
        StepVerifier.create(set.concatWith(get)).verifyComplete();

        //extend
        sleep(2000);
        Mono<Boolean> expire = bucket.expire(Duration.ofSeconds(60));
        StepVerifier.create(expire).expectNext(true).verifyComplete();

        //access expiration time
        sleep(2000);
        Mono<Void> remainTime = bucket.remainTimeToLive().doOnNext(System.out::println).then();
        StepVerifier.create(remainTime).verifyComplete();
    }

}
