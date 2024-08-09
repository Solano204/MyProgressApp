package com.example.myprogress.app.GeneralServices;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.RedisService.TokenService;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.SpringSecurity.BuildToken;
import org.springframework.beans.factory.annotation.Value;


import lombok.Data;

// This method will be used to generate the response in case such as (register, login)
@Component
@Data
public class GenerateResponse {


     private final BuildToken buildToken;
    private final MessagesFinal messagesFinal;
    private final TokenService tokenService;
    private final RegisterGeneral registerGeneral;
    private String token;
    private String refreshToken;
  
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public void  generateResponse(appUser user,Map<String, Object> body){
        token = buildToken.generateToken(user.getUser(), jwtExpiration);
        refreshToken = buildToken.generateToken(user.getUser(),refreshExpiration);
        tokenService.deleteCurrentToken(user.getUser());
        tokenService.getCurretToken(user.getUser(),token);
        body.put("token",token);
        body.put("RefreshToken", refreshToken);
        messagesFinal.fillMapInformation(body, registerGeneral.getUserRegistered(user));
    }

}
