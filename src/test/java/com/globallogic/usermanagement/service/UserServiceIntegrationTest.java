package com.globallogic.usermanagement.service;

import com.globallogic.usermanagement.bean.StatusService;
import com.globallogic.usermanagement.bean.StatusServiceEnum;
import com.globallogic.usermanagement.controller.dto.SignUpDto;
import com.globallogic.usermanagement.controller.dto.UserDTO;
import com.globallogic.usermanagement.exception.ServiceException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    private final SignUpDto signUpDtoOk = SignUpDto.builder().name("Julio Gonzalez")
            .email("user.test@dominio.com").password("Ga2asffdfdf4").build();

    @Test
    public void mustInsertWhenIsOk() throws Exception {
       StatusService statusService = userService.signUp(signUpDtoOk);
       Assert.assertEquals(StatusServiceEnum.CREATED,statusService.getStatusServiceEnum());
       List<UserDTO> userDTOS = userService.getAll();
       Assert.assertTrue(userDTOS.size()>0);
    }

    @Test
    public void mustNotInsertWhenIsDuplicated() throws Exception {
        StatusService statusService = userService.signUp(signUpDtoOk);
        Assert.assertEquals(StatusServiceEnum.CREATED,statusService.getStatusServiceEnum());
        statusService = userService.signUp(signUpDtoOk);
        Assert.assertEquals(StatusServiceEnum.DUPLICATED,statusService.getStatusServiceEnum());

    }
}
