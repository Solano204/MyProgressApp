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
                title = "My Progress", //Title of the API
                description = """
                To execute the api correctly:
                Need Use the values of example in the body or can use others values(OPTIONAL):
                \n 1- Register a new user type APP
                \n 2- Sign in with the email and password, 
               \n  3- Get the ACCESS_TOKEN from the response
                \n 4- Authorize with the ACCESS_TOKEN
                \n 5- First create the next objects{Routine, Recipe} or use all method creates (OPTIONAL)     
              \n 6- After create those objects, Follow the orden of the endpoints (They are in the order to execute them)  
                                """, // description of the api
                version = "Diet and Routine Manager Spring boot application v.1.0", // Version of the API

                contact = @Contact(
                        name = "Carlos Josue Lopez Solano", // Name of the developer
                        url = "https://github.com/Solano204/app", // Url of the developer
                        email = "carlosjosuelopezsolano98@gmail.com" // Email of the developer
                ),
                license = @License(
                        name = "My App",
                        url = "https://github.com/Solano204/app"
                )
        ),

        // Here I configure my servers
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
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