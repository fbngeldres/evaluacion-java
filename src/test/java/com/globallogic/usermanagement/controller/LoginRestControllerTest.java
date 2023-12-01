package com.globallogic.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.usermanagement.controller.dto.UserDTO;
import com.globallogic.usermanagement.controller.dto.UserPhoneDto;

import com.globallogic.usermanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginRestController.class)
class LoginRestControllerTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private final String login= "/login";


    @Test
    public void mustBeLoginWhenIsOk() throws  Exception{
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpb0B0ZXN0";
        UserDTO userDTO = UserDTO.builder()
                .id(UUID.fromString("e5c6cf84-8860-4c00-91cd-22d3be28904e"))
                .created(LocalDate.now())
                .lastLogin(LocalDate.now())
                .token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpb0B0ZXN0")
                .isActive(true)
                .name("Julio Gonzalez")
                .email("julio@testssw.cl")
                .password("a2asfGfdfdf4")
                .phones(Arrays.asList(UserPhoneDto.builder()
                                .number(87650009)
                                .citycode(7)
                                .countrycode("25")
                        .build()))
                .build();

        given(userService.getUser(any())).willReturn(userDTO);

        mvc.perform(post(login)

                .header("authentication", "Bearer " + token)

                )

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value("e5c6cf84-8860-4c00-91cd-22d3be28904e"));

    }


}
