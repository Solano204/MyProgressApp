package com.example.myprogress.app.Entites;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication request with username, password, and authentication type")
public record AuthLoginRequest(
    @Schema(description = "Username of the user", example = "younowjs2", required = true)
    String username,
    
    @Schema(description = "Password of the user", example = "123456", required = true)
    String password,

    @Schema(description = "Type of authentication", example = "App", required = true, allowableValues = {"App", "Google"})
    String authentication
) {}