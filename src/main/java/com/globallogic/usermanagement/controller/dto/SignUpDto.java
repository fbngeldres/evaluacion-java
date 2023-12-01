package com.globallogic.usermanagement.controller.dto;

import com.globallogic.usermanagement.utils.Messages;
import com.globallogic.usermanagement.validators.EmailValidator;
import com.globallogic.usermanagement.validators.PasswordValidator;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class SignUpDto {
    private String name;
    @NotNull(message = Messages.MUST_NOT_BE_VALIDATION)
    @EmailValidator
    private String email;
    @NotNull(message = Messages.MUST_NOT_BE_VALIDATION)
    @PasswordValidator
    private String password;
    private List<SignUpPhoneDto> phones;
}
