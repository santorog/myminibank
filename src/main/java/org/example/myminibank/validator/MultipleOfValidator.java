package org.example.myminibank.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipleOfValidator implements ConstraintValidator<MultipleOf, Double> {
    private double multiple;

    @Override
    public void initialize(MultipleOf constraintAnnotation) {
        this.multiple = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) return false; // Null values are not allowed
        return Math.round(value * 100) % Math.round(multiple * 100) == 0;
    }
}
