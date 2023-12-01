package com.globallogic.usermanagement.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidatorPattern  implements ConstraintValidator<EmailValidator, String> {
    private static final String EMAIL_PATTERN = ".+@.+\\..+";
    @Override
    public void initialize(EmailValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email != null){
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
     return false;
    }
}
