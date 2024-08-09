package com.example.myprogress.app.SpringSecurity;
import com.example.myprogress.app.Entites.appUser;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Data
public class RefreshToken {

    private final LogoutService validateToken;
    private final GenerateResponse generateResponse;
    
  public void refreshToken(
    HttpServletRequest request, HttpServletResponse response, @RequestBody appUser user) throws IOException {


        if(request != null && validateToken.ValidateToken(request)){
        Map<String, Object> body = new HashMap<>();
        generateResponse.generateResponse(user, body);
        ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType(VariablesGeneral.CONTENT_TYPE); // i give the message in format JSON
    }else{
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString("No se pudo hacer el refresh necesita logearse de nuevo"));
        response.setStatus(200);
        response.setContentType(VariablesGeneral.CONTENT_TYPE); // i give the message in format JSON
    }
    }
}
