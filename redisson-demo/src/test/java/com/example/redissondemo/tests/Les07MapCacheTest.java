package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import com.example.redissondemo.dto.Student;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Les07MapCacheTest extends BaseTest {

    @Test
    public void mapCacheTest(){

        RMapCacheReactive<Integer, Student> mapCache = redissonReactiveClient.getMapCache("users:cache",
                new TypedJsonJacksonCodec(Integer.class, Student.class));

        Student student1 = new Student("Sam", 25, "Berlin", List.of(4,4,5));
        Student student2 = new Student("George", 15, "Madrid", List.of(4,3,5));


        Mono<Student> mono1 = mapCache.put(4, student1, 5, TimeUnit.SECONDS);
        Mono<Student> mono2 = mapCache.put(5, student2, 10, TimeUnit.SECONDS);
        StepVerifier.create(mono1.then(mono2).then()).verifyComplete();

        sleep(3000);

        mapCache.get(4).doOnNext(System.out::println).subscribe();
        mapCache.get(5).doOnNext(System.out::println).subscribe();

        sleep(3000);

        mapCache.get(4).doOnNext(System.out::println).subscribe();
        mapCache.get(5).doOnNext(System.out::println).subscribe();

    }


}
