package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import com.example.redissondemo.RedissonConfig;
import com.example.redissondemo.dto.Student;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LocalCachedMapOptions.ReconnectionStrategy;
import org.redisson.api.LocalCachedMapOptions.SyncStrategy;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;

public class Les08LocalCachedMap extends BaseTest {

    RLocalCachedMap<Integer, Student> studentsMap;

    @BeforeAll
    public void setUpClient() {
        RedissonConfig redissonConfig = new RedissonConfig();
        RedissonClient redissonClient = redissonConfig.getRedissonClient();
        LocalCachedMapOptions<Integer, Student> mapOptions = LocalCachedMapOptions.<Integer, Student>defaults()
                .syncStrategy(SyncStrategy.UPDATE)
                .reconnectionStrategy(ReconnectionStrategy.NONE);

        studentsMap = redissonClient.getLocalCachedMap(
                "students",
                new TypedJsonJacksonCodec(Integer.class, Student.class),
                mapOptions
        );

    }

    @Test
    public void appServerOne() {
        Student student1 = new Student("Sam", 25, "Berlin", List.of(4, 4, 5));
        Student student2 = new Student("George", 15, "Madrid", List.of(4, 3, 5));

        studentsMap.put(1, student1);
        studentsMap.put(2, student2);
        Flux.interval(Duration.ofSeconds(2))
                .doOnNext(i -> System.out.println("===>>>>>>>>>>>" + i + studentsMap.get(1))).subscribe();

        sleep(600000);
    }

    @Test
    public void appServerTwo() {
        Student student1 = new Student("Sam-updated", 25, "Berlin", List.of(4, 4, 5));
        studentsMap.put(1, student1);
    }
}
