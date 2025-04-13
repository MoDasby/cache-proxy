package com.modasby.cacheproxy.config;

import com.modasby.cacheproxy.cache.Cache;
import com.modasby.cacheproxy.cache.lru.LRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, ResponseEntity<String>> getLRUCache(@Value("${cache.capacity:100}") int capacity) {
        return new LRUCache<>(capacity);
    }
}
