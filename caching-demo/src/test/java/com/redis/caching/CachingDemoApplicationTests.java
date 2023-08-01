package com.redis.caching;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class CachingDemoApplicationTests {

    @Autowired
    ReactiveStringRedisTemplate template;

    //	@Autowired
    //	RedissonReactiveClient redissonReactiveClient;

    @RepeatedTest(3)
    void springDateRedisTest() {

        long start = System.currentTimeMillis();
        ReactiveValueOperations<String, String> stringStringReactiveValueOperations = template.opsForValue();
        Mono<Void> then = Flux.range(0, 500_000)
                .flatMap(i -> stringStringReactiveValueOperations.increment("user:1:visit")).then();
        StepVerifier.create(then).verifyComplete();
        long finish = System.currentTimeMillis();
        System.out.println((double) (finish - start) / 1000 + " s");
    }

    //	@RepeatedTest(3)
    //	void redissonTest() {
    //
    //		long start = System.currentTimeMillis();
    //		RAtomicLongReactive atomicLong = redissonReactiveClient.getAtomicLong("user:2:visit");
    //		Mono<Void> then = Flux.range(0, 500_000)
    //				.flatMap(i -> atomicLong.incrementAndGet()).then();
    //		StepVerifier.create(then).verifyComplete();
    //		long finish = System.currentTimeMillis();
    //		System.out.println((double)(finish - start)/1000 + " s");
    //	}

}
