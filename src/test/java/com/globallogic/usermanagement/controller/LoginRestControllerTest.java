package com.globallogic.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.usermanagement.controller.dto.ResponseDto;
import com.globallogic.usermanagement.controller.dto.SignUpDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class LoginRestControllerTest {

    @Autowired
    private MockMvc mvc;

/*
    @MockBean
    private UserService userService;*/
    @Autowired
    private ObjectMapper objectMapper;

    private final String login= "/login";

    private final String signUpUrl= "/sign-up";
    @Test
    public void mustBeLoginWhenIsOk() throws  Exception{

        SignUpDto signUpDtoOk = SignUpDto.builder().email("user.test@dominio.com").password("Ga2asffdfdf4").build();
        MvcResult result = mvc.perform(post(signUpUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpDtoOk))
        ).andReturn();


        ResponseDto responseObj = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseDto.class);

        String token =((LinkedHashMap<String,String>)responseObj.getMessage()).get("token");


        mvc.perform(post(login)

                .header("Authorization", "Bearer " + token)

                )

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()) ;

    }


}
