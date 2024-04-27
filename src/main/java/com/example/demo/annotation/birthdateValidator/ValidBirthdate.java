package com.example.demo.annotation.birthdateValidator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BirthdateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthdate {
    String message() default "Invalid birthdate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
