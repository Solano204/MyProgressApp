package com.example.myprogress.app;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching // This enables Spring's annotation-driven cache management capability
public class CacheConfig {
    public static final String FIRST_URL = "UserTemp";
    public static final String SECOND_URL = "UserTemp2";
    public static final String THIRD_URL = "UserTemp3";
    public static final String USER_CACHE = "TokenCache";


    // In this example will have a list of the cache redis
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put(USER_CACHE, createConfig(10, ChronoUnit.MINUTES));
        return RedisCacheManager
            .builder(redisConnectionFactory) // Here I call the connection with Redis
            .withInitialCacheConfigurations(redisCacheConfigurationMap)// Here I call the map of the redis
            .build();
    }   

    private static RedisCacheConfiguration createConfig(long time, ChronoUnit temporalUnit) {
        RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer(new ObjectMapper());
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.of(time, temporalUnit))
            .disableCachingNullValues() // Prevents caching of null values.
            .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer())) //  This ensures that keys are stored as plain strings in Redis
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer)); //This serializer converts the values to JSON, allowing complex objects to be stored in Redis.
    }


    
    

    // Utility method to get a random cache name
    public static String getRandomCacheName(){
        String[] cacheNames = { FIRST_URL, SECOND_URL, THIRD_URL };
        int randomIndex = ThreadLocalRandom.current().nextInt(cacheNames.length);
        return cacheNames[randomIndex];
    }
}