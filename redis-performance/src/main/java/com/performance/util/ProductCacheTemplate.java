package com.performance.util;

import com.performance.entity.Product;
import com.performance.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductCacheTemplate extends CacheTemplate<Integer, Product> {

    @Autowired
    private ProductRepository productRepository;

    private RMapReactive<Integer, Product> map;

    public ProductCacheTemplate(RedissonReactiveClient redissonReactiveClient){
        map = redissonReactiveClient.getMap("product", new TypedJsonJacksonCodec(Integer.class, Product.class));
    }


    @Override
    protected Mono<Product> getFromSource(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    protected Mono<Product> getFromCache(Integer id) {
        return map.get(id);
    }

    @Override
    protected Mono<Product> updateSource(Integer id, Product product) {
        return productRepository.findById(id)
                .doOnNext(p-> product.setId(id))
                .flatMap(p -> productRepository.save(product));
    }

    @Override
    protected Mono<Product> updateCache(Integer id, Product product) {
        return map.fastPut(id, product).thenReturn(product);
    }

    @Override
    protected Mono<Void> deleteFromSource(Integer id) {
        return productRepository.deleteById(id);
    }

    @Override
    protected Mono<Void> deleteFromCache(Integer id) {
        return map.fastRemove(id).then();
    }
}
