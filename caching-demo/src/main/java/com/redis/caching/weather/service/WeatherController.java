package com.redis.caching.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("weather")
    public Mono<Integer> getInfo(@RequestParam(value = "zip", defaultValue = "0") Integer zip){
        return Mono.fromSupplier(()-> weatherService.getInfo(zip));
    }
}
