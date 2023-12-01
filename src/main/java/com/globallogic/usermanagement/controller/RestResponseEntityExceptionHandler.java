package com.globallogic.usermanagement.controller;

import com.globallogic.usermanagement.controller.dto.ErrorDto;
import com.globallogic.usermanagement.controller.dto.ResponseDto;
import com.globallogic.usermanagement.exception.ServiceException;
import com.globallogic.usermanagement.utils.Messages;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class RestResponseEntityExceptionHandler    {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {


        StringBuilder messages = new StringBuilder() ;
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            messages.append("\n-"+errorMessage);

        });
        ErrorDto responseDto = ErrorDto.builder().timeStamp(LocalDateTime.now())
                .detail(messages.toString()).codigo(HttpStatus.BAD_REQUEST.value()).build();
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleTypeMismatchExceptions(ServiceException ex) {

        ErrorDto responseDto = ErrorDto.builder().timeStamp(LocalDateTime.now())
                .detail(Messages.ERROR_UNCONTROLLED).codigo(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();


        return ResponseEntity.internalServerError().body(responseDto);
    }

}
