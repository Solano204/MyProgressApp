package com.example.myprogress.app.validations;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;

import jakarta.validation.ConstraintValidatorContext;


// This class will validate all fields of the new user to Register
@Component
public class RegisterGeneralValidations {

    List<String> listGoals;
    List<String> listLevelActivities;
    List<String> genders;

    public RegisterGeneralValidations() {
        listGoals = List.of("Perder Peso", "Mantener Peso", "Ganar Peso");
        listLevelActivities = List.of("Sedentario", "Un Poco Activo", "Moderadamente Activo", "Bastante Activo", "Super Activo");
        genders = List.of("Masculino", "Femenino");
    }

     public boolean validateFields(InfoRegister value, ConstraintValidatorContext context) {
        if (value.getAge() < 0) {
            throw new FieldIncorrectException("La edad tiene que ser mayor a 0");
        }

        if (value.getHeight()<0) {
            throw new FieldIncorrectException("La altura tiene que ser mayor a 0");
        }

        if(value.getStartingWeight()<0 ){
            throw new FieldIncorrectException("El peso inicial tiene que ser mayor a 0");
        }

        if (value.getCurrentWeight()<0) {
            throw new FieldIncorrectException("El peso actual tiene que ser mayor a 0");
        }

        if (value.getEndWeight()<0) {
            throw new FieldIncorrectException("El peso final tiene que ser mayor a 0");
        }

        if (value.getEndWeight() < value.getStartingWeight()) {
            throw new FieldIncorrectException("El peso final tiene que ser mayor o igual al inicial");
        }
        
        if (!genders.contains(value.getGender())) {
            throw new FieldIncorrectException("Genero incorrecto");
        }

        if (!listLevelActivities.contains(value.getLevelActivity())) {
            throw new FieldIncorrectException("Actividad incorrecta");
        }

        if (!listGoals.contains(value.getGoal())) {
            throw new FieldIncorrectException("Objetivo incorrecto");
        }     
        return true;
    }
}
