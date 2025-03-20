package org.example.myminibank.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MultipleOfValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipleOf {
    double value();
    String message() default "Amount must be a multiple of {value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}