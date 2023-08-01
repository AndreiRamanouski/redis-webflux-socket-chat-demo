package com.redis.caching.weather.service;

import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalServiceClient {


    @CachePut(value = "weather", key = "#zip")
    public int getWeatherInfo(int zip){
       return ThreadLocalRandom.current().nextInt(60, 100);
    }
}
