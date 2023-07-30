package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import com.example.redissondemo.dto.Student;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Les06MapTest extends BaseTest {

    @Test
    public void mapTest() {
        RMapReactive<String, String> map = redissonReactiveClient.getMap("user:1", StringCodec.INSTANCE);
        Mono<String> sam = map.put("name", "sam");
        Mono<String> age = map.put("age", "10");
        Mono<String> city = map.put("city", "Miami");

        StepVerifier.create(sam.concatWith(age).concatWith(city).then()).verifyComplete();
    }

    @Test
    public void mapTest2() {
        RMapReactive<String, String> map = redissonReactiveClient.getMap("user:1", StringCodec.INSTANCE);
        Map<String, String> javaMap = Map.of("name", "jake", "city", "Warsaw", "age", "25");
        StepVerifier.create(map.putAll(javaMap).then()).verifyComplete();
    }

    @Test
    public void mapTest3() {
        //        Map<Integer, Student>
        RMapReactive<Integer, Student> map = redissonReactiveClient.getMap("users:1",
                new TypedJsonJacksonCodec(Integer.class, Student.class));

        Student student1 = new Student("Sam", 25, "Berlin", List.of(4,4,5));
        Student student2 = new Student("George", 15, "Madrid", List.of(4,3,5));
        Student student3 = new Student("Bill", 35, "London", List.of(4,4,4));
        Map<Integer, Student> integerStudentMap = Map.of(1, student1, 2, student2, 3, student3);
        StepVerifier.create(map.putAll(integerStudentMap).then()).verifyComplete();

        Mono<Student> mono1 = map.put(4, student1);
        Mono<Student> mono2 = map.put(5, student2);
        Mono<Student> mono3 = map.put(6, student3);
        StepVerifier.create(mono1.concatWith(mono2).concatWith(mono3).then()).verifyComplete();
    }
}
