package com.redis.caching.city.client;

import com.redis.caching.city.dto.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CityClient {

    private final WebClient webClient;

    public CityClient(@Value("${city.service.url}") String url) {
        webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<City> getCity(String zipCode) {
        return webClient.get().uri("{zipCode}", zipCode).retrieve().bodyToMono(City.class);
    }

}
