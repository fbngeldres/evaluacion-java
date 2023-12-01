package com.globallogic.usermanagement.service;

import com.globallogic.usermanagement.controller.dto.UserDTO;
import com.globallogic.usermanagement.service.UserDetailsImpl;
import com.globallogic.usermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{

    private UserService userService;
    @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserDTO user = userService.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
