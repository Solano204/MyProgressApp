package com.example.myprogress.app;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // Use the first parameter as the key, assuming it's IdUser
        if (params.length > 0) {
            String h =params[0].toString(); // This will return the IdUser directly
            return h;
        }
        return null;
    }
}