package com.globallogic.usermanagement.controller.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ResponseDto {
    private HttpStatus httpStatus;
    private Object message;
}
