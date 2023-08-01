package com.redis.caching.fib.service;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FibService {

    @Cacheable("mathFib")
    public int getFib(int index) {
        log.info("Calculating fib for index: {}", index);



        int fib = fib(index);
        log.info("Returned fib: {}", fib);
        return fib;
    }

    @CacheEvict("mathFib")
    public void clearCache(int index){
        log.info("Clearing cache for key {}", index);
    }

    @Scheduled(initialDelay = 5,fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    @CacheEvict(value = "mathFib", allEntries = true)
    public void clearCache(){
        log.info("Clearing cache");
    }

    //2^N
    private int fib(int index) {
        if (index < 2) {
            return index;
        }
        return fib(index-1) + fib(index-2);

    }
}
