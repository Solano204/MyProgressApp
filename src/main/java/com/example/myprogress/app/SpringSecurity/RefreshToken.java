package com.example.myprogress.app.SpringSecurity;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.GeneralServices.GenerateResponse;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Data
public class RefreshToken {

    private final LogoutService validateToken;
    private final GenerateResponse generateResponse;
        
  public Map<String,Object> refreshToken(
    HttpServletRequest request,@RequestBody appUser user) throws IOException {
        if(request != null && validateToken.ValidateToken(request)){
        Map<String, Object> body = new HashMap<>();
        String header = request.getHeader(VariablesGeneral.AUTHORIZATION);
        String token = header.replace(VariablesGeneral.HEADER_TOKEN, "");
        generateResponse.generateResponse(user, body);
        body.put("RefreshToken", token); // i must not modify the refresh token only the access token
        ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
       return body;
    }else{
        throw new FieldIncorrectException("Invalid token");
    }
    }
}
