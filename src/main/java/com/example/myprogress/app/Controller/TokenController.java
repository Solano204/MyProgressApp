package com.example.myprogress.app.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.SpringSecurity.RefreshToken;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@RestController
@Data
public class TokenController {
    private final RefreshToken refreshToken;

    // This method will be executed when the client I need a new access token
    @GetMapping("/RefreshToken")
    public void successAuthentication(HttpServletRequest request, HttpServletResponse response, @RequestBody appUser user)
            throws IOException {
        refreshToken.refreshToken(request, response,user);

    }

}
