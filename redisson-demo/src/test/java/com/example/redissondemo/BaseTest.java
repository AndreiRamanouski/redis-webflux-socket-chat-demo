package com.example.redissondemo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.redisson.api.RedissonReactiveClient;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private RedissonConfig redissonConfig = new RedissonConfig();
    protected RedissonReactiveClient redissonReactiveClient;

    @BeforeAll
    public void setClient(){
        this.redissonReactiveClient = redissonConfig.getRedissonReactiveClient();
    }

    @AfterAll
    public void shutdown(){
        this.redissonReactiveClient.shutdown();
    }

    protected void sleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
