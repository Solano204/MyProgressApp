package com.example.myprogress.app.Entites;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This class will represent the current token that User is using

@Schema(description = "Token entity representing the authentication token and associated user information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {

    @Schema(description = "JWT token string", 
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5b3Vub3dqczIiLCJleHAiOjE3MjQyODY5NjUsImlhdCI6MTcyNDIwMDU2NX0.l7tKGbff05UfD8-wwGXbkALLRP4cHKIU15RfVHrAKvU", 
            required = true)
    private String token;

    @Schema(description = "Username associated with the token", 
            example = "younowjs2", 
            required = true)
    private String user;
}
