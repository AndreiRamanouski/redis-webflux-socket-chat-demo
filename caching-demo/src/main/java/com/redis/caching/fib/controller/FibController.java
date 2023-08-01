package com.redis.caching.fib.controller;

import com.redis.caching.fib.service.FibService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class FibController {

    private final FibService fibService;

    @GetMapping("fib")
    public Mono<Integer> getFib(@RequestParam("index") Integer index){
        return Mono.fromSupplier(() -> fibService.getFib(index));
    }
    @PostMapping("fib")
    public Mono<Void> clearFib(@RequestParam("index") Integer index){
        return Mono.fromRunnable(() -> fibService.clearCache(index));
    }
}
