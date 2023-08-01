package com.redis.caching.city.service;

import com.redis.caching.city.client.CityClient;
import com.redis.caching.city.dto.City;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CityService {


    private final CityClient cityClient;

    @Cacheable("getCity")
    public Mono<City> getCity(String zipCode) {
        return cityClient.getCity(zipCode);
    }

}
