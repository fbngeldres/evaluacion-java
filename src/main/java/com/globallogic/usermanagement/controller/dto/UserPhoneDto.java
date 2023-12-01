package com.globallogic.usermanagement.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPhoneDto {
    private Long number;
    private Integer citycode;
    private String countrycode;
}
