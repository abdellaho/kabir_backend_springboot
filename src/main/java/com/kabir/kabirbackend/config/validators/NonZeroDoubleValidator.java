package com.kabir.kabirbackend.config.validators;


import com.kabir.kabirbackend.config.annotations.NotEqualZeroDouble;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NonZeroDoubleValidator implements ConstraintValidator<NotEqualZeroDouble, Double> {
    @Override
    public void initialize(NotEqualZeroDouble constraintAnnotation) {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null is allowed (e.g., optional fields); adjust to false if null should be invalid
        }
        return value != 0;
    }
}