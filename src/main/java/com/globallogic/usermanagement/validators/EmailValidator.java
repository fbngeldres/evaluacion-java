package com.globallogic.usermanagement.validators;

import com.globallogic.usermanagement.utils.Messages;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidatorPattern.class)
@Documented
public @interface EmailValidator {
    String message() default Messages.USER_EMAIL_VALIDATION;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
