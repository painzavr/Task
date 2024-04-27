package com.example.demo.annotation.birthdateValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class BirthdateValidator implements ConstraintValidator<ValidBirthdate, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        // null values are valid
        if (birthDate == null) {
            return false;
        }
        return birthDate.isBefore(LocalDate.now());
    }
}
