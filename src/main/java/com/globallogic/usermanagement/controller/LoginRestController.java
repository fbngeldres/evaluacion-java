package com.globallogic.usermanagement.controller;

import com.globallogic.usermanagement.controller.dto.UserDTO;
import com.globallogic.usermanagement.service.UserDetailsImpl;
import com.globallogic.usermanagement.service.UserService;
import com.globallogic.usermanagement.utils.JwtUtils;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginRestController {
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    @PostMapping
    public ResponseEntity<UserDTO> login() throws Exception{
        UserDetailsImpl userDetails =
                (UserDetailsImpl ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String jwt = jwtUtils.generateJwtToken(SecurityContextHolder.getContext().getAuthentication());
        UserDTO userDTO =userService.loginUser(userDetails.getId());
        userDTO.setToken(jwt);
       return ResponseEntity.ok(userDTO);
    }
}
