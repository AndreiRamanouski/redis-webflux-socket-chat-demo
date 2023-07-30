package com.example.redissondemo.tests;

import com.example.redissondemo.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RTransactionReactive;
import org.redisson.api.TransactionOptions;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec14TransactionTest extends BaseTest {

    RBucketReactive<Long> userOneBalance;
    RBucketReactive<Long> userTwoBalance;

    @BeforeAll
    public void accountSetup(){
        userOneBalance = redissonReactiveClient.getBucket("user:1:balance", LongCodec.INSTANCE);
        userTwoBalance = redissonReactiveClient.getBucket("user:2:balance", LongCodec.INSTANCE);
        Mono<Void> then = userOneBalance.set(100l)
                .then(userTwoBalance.set(0l))
                .then();
        StepVerifier.create(then).verifyComplete();
    }

    @AfterAll
    public void accountBalanceStatus(){
        Mono<Void> then = Flux.zip(userOneBalance.get(), userTwoBalance.get()).doOnNext(System.out::println).then();
        StepVerifier.create(then).verifyComplete();
    }

    //user:1:balance 100
    //user:2:balance 0
    @Test
    public void nonTransactionalTest() {
        transfer(userOneBalance, userTwoBalance, 50)
                .thenReturn(0)
                .map(integer -> (5 / integer)) //some error
                .doOnError(System.out::println)
                .subscribe();

        sleep(3000);

    }

    @Test
    public void transactionalTest() {
        RTransactionReactive transaction = redissonReactiveClient.createTransaction(TransactionOptions.defaults());
        RBucketReactive<Long> transactionBucketOne = transaction.getBucket("user:1:balance", LongCodec.INSTANCE);
        RBucketReactive<Long> transactionBucketTwo = transaction.getBucket("user:2:balance", LongCodec.INSTANCE);

        transfer(transactionBucketOne, transactionBucketTwo, 50)
                .thenReturn(0)
                .map(integer -> (5 / integer)) //some error
                .then(transaction.commit())
                .doOnError(System.out::println)
                .onErrorResume(ex -> transaction.rollback())
                .subscribe();

        sleep(3000);

    }

    private Mono<Void> transfer(RBucketReactive<Long> from, RBucketReactive<Long> to, int amount) {

        return Flux.zip(from.get(), to.get()) // [b1,b2]
                .filter(t -> t.getT1() >= amount)
                .flatMap(t -> from.set(t.getT1() - amount).thenReturn(t))
                .flatMap(t -> to.set(t.getT2() + amount))
                .then();
    }

}
