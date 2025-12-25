package com.kabir.kabirbackend.config.validators;


import com.kabir.kabirbackend.config.annotations.NotEqualZero;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NonZeroIntegerValidator implements ConstraintValidator<NotEqualZero, Integer> {
    @Override
    public void initialize(NotEqualZero constraintAnnotation) {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null is allowed (e.g., optional fields); adjust to false if null should be invalid
        }
        return value != 0;
    }
}