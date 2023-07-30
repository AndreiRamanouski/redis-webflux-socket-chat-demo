package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Les05EventListenerTest extends BaseTest {


    @Test
    public void expiredEventTest() {
        RBucketReactive<Object> bucket = redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", Duration.ofSeconds(1));
        Mono<Void> get = bucket.get().doOnNext(System.out::println).then();
        Mono<Void> event = bucket.addListener((ExpiredObjectListener) name -> System.out.println("Expired : " + name))
                .then();
        StepVerifier.create(set.concatWith(get).concatWith(event)).verifyComplete();

        //extend
        sleep(3000);
    }

    @Test
    public void deletedEventTest() {
        RBucketReactive<Object> bucket = redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam");
        Mono<Void> get = bucket.get().doOnNext(System.out::println).then();
        Mono<Void> event = bucket.addListener((DeletedObjectListener) name -> System.out.println("Deleted : " + name))
                .then();
        StepVerifier.create(set.concatWith(get).concatWith(event)).verifyComplete();

        //extend
        sleep(5000);
    }
}
