package com.globallogic.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.usermanagement.controller.dto.SignUpDto;
import com.globallogic.usermanagement.controller.dto.SignUpPhoneDto;
import com.globallogic.usermanagement.exception.ServiceException;
import com.globallogic.usermanagement.service.UserService;
import com.globallogic.usermanagement.utils.Messages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SignUpControllerExceptionTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String signUpUrl= "/sign-up";


    private final SignUpDto signUpDtoOk = SignUpDto.builder().email("user.test2@dominio.com").password("Ga2asffdfdf4")
            .phones(Arrays.asList(SignUpPhoneDto.builder()
                            .countrycode("+51")
                            .number(12345678L)
                            .citycode(54)
                    .build())).build();


    @Test
    public void mustSingUpWhenIsOk() throws  Exception{

    when(userService.signUp(any())).thenThrow(ServiceException.class);

        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoOk))
                )

                .andExpect(status().isInternalServerError());


    }

}
