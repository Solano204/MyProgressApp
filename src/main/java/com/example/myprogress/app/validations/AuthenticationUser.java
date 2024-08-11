package com.example.myprogress.app.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;



// Annotation relationed in the case of the class AuthenticationUser.java
@Constraint(validatedBy = LogicValidateAuthentication.class) 
@Retention(RetentionPolicy.RUNTIME) 
@Target({ ElementType.TYPE, ElementType.FIELD }) 
public @interface AuthenticationUser {
    String message() default " Ingreso incorrecto por favor verifique sus credenciales";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
