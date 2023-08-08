package com.performance.service;

import com.performance.entity.Product;
import com.performance.util.CacheTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceV2 {

    private final CacheTemplate<Integer, Product> productCacheTemplate;

    public Mono<Product> getProduct(int id) {
        log.info("getProduct by Id : {}", id);
        return productCacheTemplate.get(id).doOnNext(System.out::println);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        log.info("updateProduct Id : {}", id);
        return productMono.flatMap(e -> productCacheTemplate.update(id, e)).doOnNext(pr -> pr.setId(id));
    }

    public Mono<Void> delete(int id){
        return productCacheTemplate.delete(id);
    }

}
