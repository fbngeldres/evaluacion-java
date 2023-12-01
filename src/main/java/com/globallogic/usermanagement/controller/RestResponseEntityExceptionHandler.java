package com.globallogic.usermanagement.controller;

import com.globallogic.usermanagement.controller.dto.ErrorDto;
import com.globallogic.usermanagement.exception.ServiceException;
import com.globallogic.usermanagement.utils.Messages;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        ErrorDto responseDto = ErrorDto.builder().timeStamp(LocalDateTime.now())
                .detail(Messages.USER_DUPLICATED_MESSAGE).codigo(HttpStatus.CONFLICT.value()).build();


        return ResponseEntity.unprocessableEntity().body(responseDto);
    }

}
