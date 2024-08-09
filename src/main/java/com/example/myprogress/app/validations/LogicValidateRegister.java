package com.example.myprogress.app.validations;

import java.util.List;

import org.apache.tomcat.util.openssl.pem_password_cb;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;
import lombok.val;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE) 
// This class will validate all field of the register
public class LogicValidateRegister implements ConstraintValidator<RegisterInformation, InfoRegister> {
    RegisterGeneralValidations registerGeneralValidations;

    public LogicValidateRegister( RegisterGeneralValidations registerGeneralValidations) {
        this.registerGeneralValidations = registerGeneralValidations;
    }

    @Override
    public boolean isValid(InfoRegister value, ConstraintValidatorContext context) {

        registerGeneralValidations.validateFields(value, context);   
        return true;
    }
}
