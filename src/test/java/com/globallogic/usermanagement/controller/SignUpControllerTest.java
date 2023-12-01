package com.globallogic.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.usermanagement.controller.dto.SignUpDto;
import com.globallogic.usermanagement.utils.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {


    @Autowired
    private MockMvc mvc;


    @Autowired
    private ObjectMapper objectMapper;

    private final String signUpUrl= "/sign-up";


    private final SignUpDto signUpDtoOk = SignUpDto.builder().email("user.test2@dominio.com").password("Ga2asffdfdf4").build();

    private final SignUpDto signUpDtoEmailValidation = SignUpDto.builder().email("user.test").build();
    @Test
    public void mustSingUpWhenIsOk() throws  Exception{



        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoOk))
        )

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").exists());


    }

    @Test
    public void mustNotSingUpWhenIsDuplicated() throws  Exception{

         SignUpDto signUpDtoDuplicated = SignUpDto.builder().email("user.test@dominio.com").password("Ga2asffdfdf4").build();

        mvc.perform(post(signUpUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpDtoDuplicated))
        );

        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoDuplicated))
                )

                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.detail").exists())
                .andExpect(jsonPath("$.detail").value(Messages.USER_DUPLICATED_MESSAGE))
                .andExpect(jsonPath("$.codigo").exists())
                .andExpect(jsonPath("$.codigo").value(HttpStatus.CONFLICT.value()));

    }



    @Test
    public void mustResponseBadRequestWhenEmailNotValid() throws  Exception{



        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoEmailValidation))
                )

                .andExpect(status().isBadRequest());



    }

    @Test
    public void mustResponseBadRequestWhenPasswordNotValid() throws  Exception{

        SignUpDto signUpDtoPasswordValidation = SignUpDto.builder().email("user.test@dominio.com").password("1423").build();




        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoPasswordValidation))
                )

                .andExpect(status().isBadRequest());



    }
}
