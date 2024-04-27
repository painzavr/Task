package com.example.demo.annotation.ageValidator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;


import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {
    @Value("${user.age}")
    private int age;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        // null values are valid
        if (birthDate == null) {
            return false;
        }


        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        return period.getYears() >= age;
    }
}
