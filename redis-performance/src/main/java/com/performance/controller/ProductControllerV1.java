package com.performance.controller;

import com.performance.entity.Product;
import com.performance.service.ProductServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
@Slf4j
public class ProductControllerV1 {

    private final ProductServiceV1 productService;


    @GetMapping("{id}")
    public Mono<Product> getProduct(@PathVariable Integer id){
        log.info("Get: {}", id);
        return productService.getProduct(id);
    }


    @PutMapping()
    public Mono<Product> updateProduct(@RequestBody Product product){
        log.info("Update: {}", product);
        return productService.updateProduct(product.getId(), Mono.just(product));
    }



}
