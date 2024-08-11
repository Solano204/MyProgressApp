package com.example.myprogress.app.RedisService;

import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.Token;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Data
public class TokenServices {

    private final RedisTemplate<String, Object> redisTemplate;

    long time;
    TimeUnit timeUnit = TimeUnit.SECONDS;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public void saveToken(Token token ) {
        String key = token.getUser();
        redisTemplate.opsForValue().set(key, token, jwtExpiration, timeUnit);
    }

    public Token getTokenByUser(String user) {
        return (Token) redisTemplate.opsForValue().get(user);
    }

    /*public void updateToken(Token token) {
        saveToken(token, j, timeUnit);
    }*/

    // Delete token from cache
    public void deleteToken(String user) {
        redisTemplate.delete(user);
    }

    public boolean existsByUser(String user) {
        return redisTemplate.hasKey(user);
    }
}