package com.globallogic.usermanagement.controller;

import com.globallogic.usermanagement.bean.StatusService;
import com.globallogic.usermanagement.bean.StatusServiceEnum;
import com.globallogic.usermanagement.controller.dto.ResponseDto;
import com.globallogic.usermanagement.controller.dto.ResultSignUpDto;
import com.globallogic.usermanagement.controller.dto.SignUpDto;
import com.globallogic.usermanagement.service.UserService;
import com.globallogic.usermanagement.utils.JwtUtils;
import com.globallogic.usermanagement.utils.Messages;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sign-up")
@AllArgsConstructor
@Validated
public class SignUpRestController {

    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    @PostMapping
    public ResponseEntity<ResponseDto> signUp( @Valid @RequestBody SignUpDto signUpDTO) throws Exception {
        String passwordDecrypt = signUpDTO.getPassword();
        signUpDTO.setPassword(encoder.encode(signUpDTO.getPassword()));
        StatusService statusService = userService.signUp(signUpDTO);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpDTO.getEmail(), passwordDecrypt));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        ResultSignUpDto resultSignUpDto = (ResultSignUpDto) statusService.getMessage();
        resultSignUpDto.setToken(jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(resultSignUpDto).build());
    }
}
