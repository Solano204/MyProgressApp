package com.example.myprogress.app.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.example.myprogress.app.Entites.AuthLoginRequest;
import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.InfosLogged;
import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Entites.Token;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.FeauturesServices.RecipesService;
import com.example.myprogress.app.FeauturesServices.RoutineService;
import com.example.myprogress.app.GeneralServices.GenerateResponse;
import com.example.myprogress.app.GeneralServices.GeneratorDataUser;
import com.example.myprogress.app.RedisService.TokenServices;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.example.myprogress.app.updateInformationService.updateInformationUserService;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.Data;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
@Data 
@RequestMapping("/updateInformation")

@RestController
public class UpdateInformationController {

    private final GeneratorDataUser generatorDataUser;
    private final updateInformationUserService updateInformationUserService;
    private final caloriesIntakeService caloriesIntakeService;
    private final  TokenServices tokenServices;
    private final GenerateResponse generateResponse;
    private final RoutineService routineService;
    private final RecipesService recipesService;


    @Operation(
        summary = "Change user ",
        description = "Update only the user.",
        tags = {"User Management"},
        parameters = {
            @Parameter(
                name= "newUser",
                description = "The new username to update the user with. If not provided or invalid, returns an error.",
                
                in = ParameterIn.QUERY,
                schema = @Schema(type = "string", defaultValue = "notFound")
            )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "The user entity containing updated information. The password and other attributes are also included.",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthLoginRequest.class)
            )
        ),
        responses = {
             @ApiResponse(
                responseCode = "201",
                description = "Routine successfully generated",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(oneOf = { appUser.class})
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request parameters",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "The user couldn't be updated"))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Server error while updating the user",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "The user couldn't be updated"))
            )
        }
    )
    // This method change the user 
    @PostMapping("/ChangeUser")
    public ResponseEntity<?> changeUserNames(
            @Valid @RequestBody AuthLoginRequest userInformation,
            @DefaultValue("notFound") @RequestParam String newUser) { // I put the app User because this is the entity
                                                                      // that has all attributes
        // (inclusive the password)
                appUser user = new appUser();
                user.setTypeAuthentication(userInformation.authentication());
                user.setPassWord(userInformation.password());
                user.setUser(userInformation.username());
                
        user.setInfoLogged(new InfosLogged());
        if (newUser.equals("notFound")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El usuario esta vacio");
        }

        if (updateInformationUserService.changeUser(user, newUser)) {
            // Here I get the new information with the new user
            caloriesIntakeService.changeDocumentId(user.getUser(), newUser);
            routineService.updateUserInRoutines(user.getUser(), newUser);
            recipesService.updateUserInRecipes(user.getUser(), newUser);
            tokenServices.deleteToken(user.getUser());
            Map<String, Object> body = new HashMap<>();
            user.setUser(newUser);
            generateResponse.generateResponse(user, body);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The user couldn't be updated ");
    }




    @Operation(
    summary = "Update user data",
    description = "Allows users to update selected data such as age, gender, height, goal, and activity, which may affect their progress. The request body should include the updated user information. The response includes the updated user details and an evaluation of the user's objectives.Here The password,email y user wont be affected, But the user and authentication have to be correct.",
    tags = {"User Management"},
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The user entity containing updated information including age, gender, height, goal, and activity. Validation is performed based on the `ValidationOnlyRegisterGroup` group.",
        required = true,
        content = @Content(
            mediaType = "application/json",
                    schema = @Schema(oneOf = { appUser.class})
        )
    ),
    responses = {
        @ApiResponse(
            responseCode = "201",
            description = "User data updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(oneOf = { appUser.class})
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error occurred during the update process",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "The user couldn't be updated"))
        )
    }
)
    // This method will execute each the user decide change its data (age, gender, height, goal, activity) that couuld affect the progress of the user
    @PostMapping("/updateData")
    public ResponseEntity<?> modifyDataSelected(
            @Validated(ValidationOnlyRegisterGroup.class) @RequestBody appUser user) {
        user.setInfoLogged(new InfosLogged());
        if (updateInformationUserService.updateInformationUserRecommendedData(user)) {
            // Here I get the new information with the new user
            Map<String, Object> response = new HashMap<>();
            appUser appUser = updateInformationUserService.getDataUpdated(user.getUser(), user.getTypeAuthentication());
            response.put("user", appUser);
            response.put("ProgresObjetive", updateInformationUserService.evaluateObjetive(user));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The user couldn't be updated ");
    }



    @Operation(
    summary = "Change user password",
    description = "Allows users to change their password. The request requires the old password, new password, username, and type of authentication. Returns the updated user information upon successful password change.",
    tags = {"User Management"},
    parameters = {
        @Parameter(name = "newPass", description = "The new password for the user", required = true, in = ParameterIn.QUERY),
        @Parameter(name = "user", description = "The username of the user whose password is to be changed", required = true, in = ParameterIn.QUERY),
        @Parameter(name = "oldPass", description = "The current password of the user", required = true, in = ParameterIn.QUERY),
        @Parameter(name = "typeAuthentication", description = "The type of authentication used (e.g., Google, Facebook)", required = true, in = ParameterIn.QUERY)
    },
    responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Password changed successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(oneOf = { appUser.class,Token.class})
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request due to missing or incorrect parameters",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "string", example = "The password couldn't be updated")
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error occurred during the password change process",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "string", example = "The password couldn't be updated")
            )
        )
    }
)
    // This method to change the password
    @PostMapping("/ChangePassword")
    public ResponseEntity<?> changePassword(
            @DefaultValue("notFound") @RequestParam String newPass,
            @DefaultValue("notFound") @RequestParam String user,
            @DefaultValue("notFound") @RequestParam String oldPass,
            @DefaultValue("notFound") @RequestParam String typeAuthentication) {

        if (user.equals("notFound") || typeAuthentication.equals("notFound") || oldPass.equals("notFound") || newPass.equals("notFound")) {
        return ResponseEntity.status(HttpStatus.CREATED).body("algun campo esta vacio ");
        }
        
        if (updateInformationUserService.changePassword(newPass, user, oldPass) != 0) {
            // Here I get the new information with the new user

            appUser appUser = updateInformationUserService.getDataUpdated(user, typeAuthentication);
            appUser.setPassWord(newPass);
            return ResponseEntity.status(HttpStatus.CREATED).body(appUser);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("No se pudo cambiar el password ");
    }




    @Operation(
    summary = "Delete a user",
    description = "Deletes a user from the system, including associated data such as calorie intake records, tokens, routines, and recipes. The request requires the username and type of authentication. Returns a success message if the deletion is successful, or an error message if it fails.",
    tags = {"User Management"},
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The user entity containing updated information including age, gender, height, goal, and activity. Validation is performed based on the `ValidationOnlyRegisterGroup` group.",
        required = true,
        content = @Content(
            mediaType = "application/json",
                    schema = @Schema(oneOf = { AuthLoginRequest.class})
        )
    ),
    responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User deleted successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "string", example = "User deleted successfully")
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error occurred during the user deletion process",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "string",example = "The user couldn't be deleted")
            )
        )
    }
)
    // This method to delete the user
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(
        @RequestBody AuthLoginRequest userDelete) {

            appUser user = new appUser();
            user.setUser(userDelete.username());
            user.setTypeAuthentication(userDelete.authentication());
            user.setPassWord(userDelete.password());
        if (updateInformationUserService.deleteUser(user)) {
            caloriesIntakeService.deleteById(user.getUser());
            tokenServices.deleteToken(user.getUser());
            routineService.deleteRoutinesByUser(user.getUser());
            recipesService.deleteRecipesByUser(user.getUser());
            // Here I get the new information with the new user
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("The user couldn't be deleted ");
    }

}