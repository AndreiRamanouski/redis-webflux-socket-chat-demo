package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import com.example.redissondemo.dto.Student;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec02KeyValueObjectTest extends BaseTest {

    @Test
    public void keyValueObjectTest() {

        Student student = new Student("Billy", 30, "Paris", List.of(4, 5, 6));
        //        RBucketReactive<Student> bucket = redissonReactiveClient.getBucket("student:1", JsonJacksonCodec.INSTANCE);
        //to remove  "@class\":\"com.example.redissondemo.dto.Student\"
        RBucketReactive<Student> bucket = redissonReactiveClient.getBucket("student:1",
                new TypedJsonJacksonCodec(Student.class));

        Mono<Void> set = bucket.set(student);
        Mono<Void> get = bucket.get().doOnNext(System.out::println).then();
        StepVerifier.create(set.concatWith(get)).verifyComplete();
    }
}
