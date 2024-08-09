package com.example.myprogress.app.RedisService;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.myprogress.app.CustomKeyGenerator;
import com.example.myprogress.app.Entites.Token;
import lombok.Data;




// In this class I handle the Cache and the database, All actions or activities of the database will affect the cache 
@Service
@Data
@Lazy

public class TokenService {

    private  Token lastToken;
    private final CacheManager cacheManager;
    //This method update the token using the same key
    @Cacheable(value = "TokenCache", keyGenerator = "customKeyGenerator", unless = "#result == null")
    public Token getCurretToken(String IdUser, String token) {
        lastToken = new Token();
        lastToken.setToken(token);
        lastToken.setUser(IdUser);
    return new Token(IdUser, token);
    }

    // This method update the token using a different key
    @CacheEvict(value = "TokenCache", key = "#IdUser", condition = "#result != null")
        @Cacheable(value = "TokenCache", key = "#IdUser", unless = "#result == null",condition = "#result != null")
    public Token updateCurrentTokenDifferentKey(final String oldUser, String newUser, String token) {
        return new Token (newUser, token);
    }


    @Cacheable(value = "TokenCache", key = "#IdUser", unless = "#result == null",condition = "#result != null")
    public Token updateCurrentToken(String newUser, String token) {
        return new Token (newUser, token);
    }

    //This method delete the token  using the IdUser
    @CacheEvict(value = "TokenCache", keyGenerator = "customKeyGenerator", condition = "#result == true")
    public boolean deleteCurrentToken( String IdUser) {
        lastToken = null;
        return true;
    }

    /*
     public Token getCurretToken(String user, String token) {
        String cacheKey = user;
        Cache cache = cacheManager.getCache("TokenCache");
        if (cache == null) {
            throw new IllegalArgumentException("Cache not found: " + "TokenCache");
        }

        // Try to get the value from the cache before query the database
        Token currentToken = cache.get(cacheKey, Token.class);
        if (currentToken != null) {
            return currentToken;
        }

        // If not present in cache, load the value (simulating call the database)
        Token generatedToken = new Token(user,token);
        // Store the value in the cache
        cache.put(cacheKey, generatedToken);
        return generatedToken;
    } 
        */


    // In this method I get the token from the cache
    public Object isValidCache(String key) {
        String user = key; // Replace with the actual user
        String cacheKey = "\"TokenCache::" + user + "\"";
        Cache cache = cacheManager.getCache("TokenCache");
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                Object j = valueWrapper.get(); // This will return the cached TOKEN
                String s = j.toString();
                return j;
            }
        }
        return null; // Return null if cache or value is not found
    }





}
