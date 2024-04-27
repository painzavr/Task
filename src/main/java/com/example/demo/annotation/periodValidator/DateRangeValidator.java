package com.example.demo.annotation.periodValidator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, DateRange> {

    @Override
    public boolean isValid(DateRange value, ConstraintValidatorContext context) {
        // null values are valid
        if (value.getFromDate() == null || value.getToDate() == null) {
            return false;
        }
        return value.getFromDate().isBefore(value.getToDate());
    }
}
