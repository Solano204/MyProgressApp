package com.example.myprogress.app.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.GeneralServices.GeneratorDataUser;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.example.myprogress.app.updateInformationService.updateInformationUserService;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import jakarta.validation.Valid;

@RequestMapping("/updateInformation")
@RestController
public class UpdateInformationController {

    GeneratorDataUser generatorDataUser;
    updateInformationUserService updateInformationUserService;
    private final caloriesIntakeService caloriesIntakeService;

    public UpdateInformationController(GeneratorDataUser generatorDataUser,
            updateInformationUserService updateInformationUserService, caloriesIntakeService caloriesIntakeService) {
        this.generatorDataUser = generatorDataUser;
        this.caloriesIntakeService = caloriesIntakeService;
        this.updateInformationUserService = updateInformationUserService;
    }

    // lacked of it send the data the user related with its progress, Right now the
    // new user now are registered in their own table(User,email,pas,type)
    @PostMapping("/ChangeData/{newUser}")
    public ResponseEntity<?> changeData(
            @Valid @RequestBody appUser user,
            @DefaultValue("notFound") @PathVariable String newUser) { // I put the app User because this is the entity
                                                                      // that has all attributes
        // (inclusive the password)
        user.setInfoLogged(new infoLogged());
        if (newUser.equals("notFound")) {
            throw new UnsuccessfulRegisterException("El usuario no puede estar vacío");
        }

        if (updateInformationUserService.changeUser(user, newUser)) {
            // Here I get the new information with the new user
            caloriesIntakeService.changeDocumentId(user.getUser(), newUser);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(updateInformationUserService.getDataUpdated(newUser, user.getTypeAuthentication()));
        }
        throw new UnsuccessfulRegisterException("The user couldn't be updated "); // throw an exception
    }

    // This method will execute each the user decide change its data
    @PostMapping("/updateData")
    public ResponseEntity<?> modifyDataSelected(
            @Validated(ValidationOnlyRegisterGroup.class) @RequestBody appUser user) {
        user.setInfoLogged(new infoLogged());
        if (updateInformationUserService.updateInformationUserRecommendedData(user)) {
            // Here I get the new information with the new user
            Map<String, Object> response = new HashMap<>();
            appUser appUser = updateInformationUserService.getDataUpdated(user.getUser(), user.getTypeAuthentication());
            response.put("user", appUser);
            response.put("ProgresObjetive", updateInformationUserService.evaluateObjetive(user));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        throw new UnsuccessfulRegisterException("The user couldn't be updated "); // throw an exception
    }

    // This method to change the password
    @PostMapping("/ChangePassword")
    public ResponseEntity<?> changePassword(
            @DefaultValue("notFound") @RequestParam String newPass,
            @DefaultValue("notFound") @RequestParam String user,
            @DefaultValue("notFound") @RequestParam String oldPass,
            @DefaultValue("notFound") @RequestParam String typeAuthentication) { // I put the app User because this is
                                                                                 // the entity that has all
        // attributes
        // (inclusive the password)

        if (user.equals("notFound") || typeAuthentication.equals("notFound") || oldPass.equals("notFound") || newPass.equals("notFound")) {
            throw new FieldIncorrectException("Algun dato esta incorrecto");
        }
        
        if (updateInformationUserService.changePassword(newPass, user, oldPass) != 0) {
            // Here I get the new information with the new user
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updateInformationUserService.getDataUpdated(user, typeAuthentication));
        }

        throw new UnsuccessfulRegisterException("No se pudo cambiar el password "); // throw an exception
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(
            @Valid @RequestBody appUser user) { // I put the app User because this is the entity that has all
                                                // attributes
                                                // (inclusive the password)
        if (updateInformationUserService.deleteUser(user)) {
            caloriesIntakeService.deleteById(user.getUser());
            // Here I get the new information with the new user
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User deleted successfully");
        }
        throw new UnsuccessfulRegisterException("The user couldn't be deleted "); // throw an exception
    }

}