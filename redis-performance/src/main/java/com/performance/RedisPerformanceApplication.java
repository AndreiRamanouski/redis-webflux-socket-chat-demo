package com.performance;

import com.performance.entity.Product;
import com.performance.repository.ProductRepository;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.redisson.RedissonReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RequiredArgsConstructor
public class RedisPerformanceApplication implements CommandLineRunner {

    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(RedisPerformanceApplication.class, args);
    }

    //	populate table
    @Override
    public void run(String... args) throws Exception {

        //        Mono<Void> insert = Flux.range(1, 1000)
        //                .map(i -> new Product(null, "product " + i, ThreadLocalRandom.current().nextDouble(1, 2000)))
        //                .collectList()
        //                .flatMapMany(l -> productRepository.saveAll(l))
        //                .then();
        //        insert.subscribe();
    }
}
