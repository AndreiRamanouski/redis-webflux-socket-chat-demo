package com.performance.service;

import com.performance.entity.Product;
import com.performance.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceV1 {

    private final ProductRepository productRepository;

    public Mono<Product> getProduct(int id) {
        log.info("getProduct by Id : {}", id);
        return productRepository.findById(id).doOnNext(System.out::println);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        log.info("updateProduct Id : {}", id);
         return productRepository.findById(id).flatMap(p -> productMono.doOnNext(pr -> pr.setId(id)))
                 .flatMap(productRepository::save).doOnNext(System.out::println);
    }



}
