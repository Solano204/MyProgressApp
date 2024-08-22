package com.example.myprogress.app;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

// Here I configure my swagger api
@OpenAPIDefinition(
        info = @Info(
                title = "API CLUBES", //Title of the API
                description = "Our app provides a concise listing of football team names", // description of the api
                termsOfService = "www.learningSweager.com/terminos_y_condiciones", // Url To see the termino y condiitons
                version = "1.0.0", // Version of the API

                contact = @Contact(
                        name = "Santiago Perez", // Name of the developer
                        url = "https://unprogramadornace.com", // Url of the developer
                        email = "carlosjosuelopezsolano98@gmail.com" // Email of the developer
                ),
                license = @License(
                        name = "Standard Software Use License for UnProgramadorNace",
                        url = "www.unprogramadornace.com/licence"
                )
        ),

        // Here I configure my servers
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "http://unprogramadornace:8080"
                )
        },
        security = @SecurityRequirement(
                name = "Security Token" // Here I call the scheme of authentication or security
        )
)

//Here I configure my security access
@SecurityScheme(
        name = "Security Token",
        description = "Access Token For My API",
        type = SecuritySchemeType.HTTP, // This is a http scheme to use JWT
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {}