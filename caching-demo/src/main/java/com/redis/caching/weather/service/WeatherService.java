package com.redis.caching.weather.service;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final ExternalServiceClient externalServiceClient;

    @Cacheable("weather")
    public int getInfo(int zip) {
        log.info("Get info for zip code: {}", zip);
        return 0;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void update() {
        log.info("Update");
        IntStream.rangeClosed(1, 5)
                .forEach(externalServiceClient::getWeatherInfo);
    }
}
