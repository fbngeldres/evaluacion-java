package com.globallogic.usermanagement.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidatorPattern implements ConstraintValidator<PasswordValidator, String> {

    private static final String PASSWORD_PATTERN = "^(?=(?:.*[A-Z]){1})(?=(?:.*[a-z]){1,})(?=(?:.*\\d){2})(?!.*[\\W_])(?=.{8,12}$).*$";
    @Override
    public void initialize(PasswordValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password != null){
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }
      return false;
    }
}
