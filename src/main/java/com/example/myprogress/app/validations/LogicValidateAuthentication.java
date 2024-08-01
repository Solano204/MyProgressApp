package com.example.myprogress.app.validations;

import java.util.List;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Repositories.AppUserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 

public class LogicValidateAuthentication implements ConstraintValidator<AuthenticationUser, appUser > {
    private final AppUserRepository respository;
    List <String> listTypeAuthentications;

    public LogicValidateAuthentication( AppUserRepository respository) {
        this.respository = respository;
        listTypeAuthentications = List.of("App", "Facebook", "Google");
    }
    @Override
    public boolean isValid(appUser value, ConstraintValidatorContext context) {
    
        if (!listTypeAuthentications.contains(value.getTypeAuthentication())) {
            throw new FieldIncorrectException("Tipo de autenticación incorrecta");
        }

        if (value.getUser().isEmpty()) {
            throw new FieldIncorrectException("El usuario no puede estar vacío");
        }        

        return true;
    }

}
