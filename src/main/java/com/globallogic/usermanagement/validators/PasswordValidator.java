package com.globallogic.usermanagement.validators;

import com.globallogic.usermanagement.utils.Messages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidatorPattern.class)
@Documented
public @interface PasswordValidator {
    String message() default Messages.USER_PASSWORD_VALIDATION;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
