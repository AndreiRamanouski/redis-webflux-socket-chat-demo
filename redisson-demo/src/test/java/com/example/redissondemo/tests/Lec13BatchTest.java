package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import org.junit.jupiter.api.Test;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatchReactive;
import org.redisson.api.RListReactive;
import org.redisson.api.RSetReactive;
import org.redisson.client.codec.LongCodec;
import reactor.test.StepVerifier;

public class Lec13BatchTest extends BaseTest {


    @Test
    public void batchTest(){
        RBatchReactive batch = redissonReactiveClient.createBatch(BatchOptions.defaults());
        RListReactive<Long> numbersList = batch.getList("numbers-list", LongCodec.INSTANCE);
        RSetReactive<Long> numberSet = batch.getSet("numbers-set", LongCodec.INSTANCE);

        for (long i = 0; i < 20; i++) {
            numbersList.add(i);
            numberSet.add(i);
        }
        StepVerifier.create(batch.execute().then()).verifyComplete();

    }
}
