package com.kabir.kabirbackend.config.annotations;


import com.kabir.kabirbackend.config.validators.NonZeroDoubleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NonZeroDoubleValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEqualZeroDouble {
    String message() default "Integer value is zero";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
