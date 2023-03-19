package com.blog.blogsearch.common.caching;

import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager searchCacheStore = new CaffeineCacheManager("searchCacheStore");
        searchCacheStore.setCaffeineSpec(CaffeineSpec.parse("expireAfterWrite=5s"));
        return searchCacheStore;
    }
}
