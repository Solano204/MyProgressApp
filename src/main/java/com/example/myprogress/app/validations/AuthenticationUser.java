package com.example.myprogress.app.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = LogicValidateAuthentication.class) // Here I specific which class give the logic to validate the
                                                       // type of
// the field
@Retention(RetentionPolicy.RUNTIME) // Allow validate in time execution
@Target({ ElementType.TYPE, ElementType.FIELD }) // I specify that type of element will use this annotation
public @interface AuthenticationUser {
    String message() default " Ingreso incorrecto por favor verifique sus credenciales";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
