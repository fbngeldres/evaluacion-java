package com.globallogic.usermanagement.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDto {
    private LocalDateTime timeStamp;
    private Integer codigo;
    private String detail;
}
