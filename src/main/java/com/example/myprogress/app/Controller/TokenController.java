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

import com.example.myprogress.app.Entites.AuthLoginRequest;
import com.example.myprogress.app.Entites.Token;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.SpringSecurity.RefreshToken;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@RestController
@Data

public class TokenController {
    private final RefreshToken refreshToken;



    @Operation(
        summary = "Refresh Access Token",
        description = "Refreshes the access token for an authenticated user. This endpoint expects the current token in the request and the user's authentication details. It returns a new token and other relevant information in a map.",
        tags = {"Authentication"},
        parameters = {
            @Parameter(name = "refreshTokensito", example = "Your Refresh Token", description = "The name of the routine whose time duration will be updated.", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string")),
        },
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User's authentication request containing the authentication type and username. Password is not required.",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthLoginRequest.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Token refreshed successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "object", example = "{ 'token': 'newToken', 'expiry': 'expiryTime', ... }")
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized - The token is invalid or expired",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Unauthorized - The token is invalid or expired"))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error occurred during the token refresh process",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Internal server error occurred during the token refresh process"))
            )
        }
    )
    // This method will be executed when the client I need a new access token
    @GetMapping("/RefreshToken")
    public Map<String, Object> successAuthentication(HttpServletRequest refreshTokensito,@RequestBody AuthLoginRequest user)
            throws IOException {
                appUser user1 = new appUser();
                user1.setTypeAuthentication(user.authentication());
                user1.setUser(user.username());
        return refreshToken.refreshToken(refreshTokensito ,user1);
    }

}
