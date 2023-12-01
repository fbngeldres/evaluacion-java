package com.globallogic.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.usermanagement.bean.StatusService;
import com.globallogic.usermanagement.bean.StatusServiceEnum;
import com.globallogic.usermanagement.controller.dto.SignUpDto;
import com.globallogic.usermanagement.controller.dto.ResultSignUpDto;
import com.globallogic.usermanagement.exception.ServiceException;
import com.globallogic.usermanagement.service.UserService;
import com.globallogic.usermanagement.utils.Messages;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(SignUpRestController.class)
class SignUpControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private final String signUpUrl= "/sign-up";


    private final SignUpDto signUpDtoOk = SignUpDto.builder().email("user.test@dominio.com").password("Ga2asffdfdf4").build();

    private final SignUpDto signUpDtoEmailValidation = SignUpDto.builder().email("user.test").build();
    @Test
    public void mustSingUpWhenIsOk() throws  Exception{

       String uuidId ="caa19e60-b4b5-47b5-a427-5a4ff1593fbe";
        String uuidToken ="dd59a382-ccb8-4cbc-845e-131c6c51e635";
        ResultSignUpDto resultSignUpDto = ResultSignUpDto.builder()
                .id(UUID.fromString(uuidId))
                .token(uuidToken)
                .created(LocalDateTime.now()).lastLogin(LocalDateTime.now()).isActive(true).build();

        StatusService statusService = StatusService.builder()
                .statusServiceEnum(StatusServiceEnum.CREATED)
                .message(resultSignUpDto).build();


        given(userService.signUp(any())).willReturn(statusService);

        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoOk))
        )

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message.id").value("caa19e60-b4b5-47b5-a427-5a4ff1593fbe"));


    }

    @Test
    public void mustNotSingUpWhenIsDuplicated() throws  Exception{


        StatusService statusService = StatusService.builder().statusServiceEnum(StatusServiceEnum.DUPLICATED)
                .message(Messages.USER_DUPLICATED_MESSAGE).build();


        given(userService.signUp(any())).willReturn(statusService);

        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoOk))
                )

                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value(Messages.USER_DUPLICATED_MESSAGE));


    }

    @Test
    public void mustResponseMessageWhenOcurrAnError() throws  Exception{


        doThrow(ServiceException.class).when(userService).signUp(any());

        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoOk))
                )

                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.detail").exists())
                .andExpect(jsonPath("$.detail").value(Messages.ERROR_UNCONTROLLED));


    }

    @Test
    public void mustResponseBadRequestWhenEmailNotValid() throws  Exception{


        StatusService statusService = StatusService.builder().statusServiceEnum(StatusServiceEnum.CREATED)
                .message(Messages.USER_CREATED_MESSAGE).build();


        given(userService.signUp(any())).willReturn(statusService);

        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoEmailValidation))
                )

                .andExpect(status().isBadRequest());



    }

    @Test
    public void mustResponseBadRequestWhenPasswordNotValid() throws  Exception{

        SignUpDto signUpDtoPasswordValidation = SignUpDto.builder().email("user.test@dominio.com").password("1423").build();


        StatusService statusService = StatusService.builder().statusServiceEnum(StatusServiceEnum.CREATED)
                .message(Messages.USER_CREATED_MESSAGE).build();


        given(userService.signUp(any())).willReturn(statusService);

        mvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signUpDtoPasswordValidation))
                )

                .andExpect(status().isBadRequest());



    }
}
