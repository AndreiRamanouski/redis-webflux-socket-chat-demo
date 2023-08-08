package com.performance.controller;

import com.performance.entity.Product;
import com.performance.service.ProductServiceV1;
import com.performance.service.ProductServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v2")
@RequiredArgsConstructor
@Slf4j
public class ProductControllerV2 {

    private final ProductServiceV2 productService;


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

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable Integer id){
        log.info("Delete: {}", id);
        return productService.delete(id);
    }



}
