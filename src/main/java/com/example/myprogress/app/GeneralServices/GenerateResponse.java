package com.example.myprogress.app.GeneralServices;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.myprogress.app.Entites.Token;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.RedisService.TokenServices;
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
    private final RegisterGeneral registerGeneral;
    private final TokenServices tokenServices;
    private String token;
    private String refreshToken;
  
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private boolean generateRefreshToken = false; //This variable only will be used to generate the refresh token in the case of the Login and register a new user


    public void  generateResponse(appUser user,Map<String, Object> body){
        token = buildToken.generateToken(user.getUser(), jwtExpiration);
        if (generateRefreshToken) {
            refreshToken = buildToken.generateToken(user.getUser(),refreshExpiration);
        }
        
        tokenServices.deleteToken(user.getUser());
        Token newToken = new Token(token,user.getUser());
        tokenServices.saveToken(newToken);
        body.put("token",token);
        body.put("RefreshToken", refreshToken);
        messagesFinal.fillMapInformation(body, registerGeneral.getUserRegistered(user));
    }

}
