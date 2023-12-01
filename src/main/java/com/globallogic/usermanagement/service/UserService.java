package com.globallogic.usermanagement.service;

import com.globallogic.usermanagement.bean.StatusService;
import com.globallogic.usermanagement.bean.StatusServiceEnum;
import com.globallogic.usermanagement.controller.dto.*;
import com.globallogic.usermanagement.exception.ServiceException;
import com.globallogic.usermanagement.repository.PhoneRepository;
import com.globallogic.usermanagement.repository.UserRepository;
import com.globallogic.usermanagement.repository.entity.Phone;
import com.globallogic.usermanagement.repository.entity.User;
import com.globallogic.usermanagement.utils.Messages;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public StatusService signUp(SignUpDto signUpDTO) throws ServiceException {


        try {
            User user = new User();
            user.setName(signUpDTO.getName());
            user.setPassword(signUpDTO.getPassword());
            user.setEmail(signUpDTO.getEmail());

            userRepository.save(user);
            if (signUpDTO.getPhones() != null) {
                user.setPhones(signUpDTO.getPhones().stream()
                        .map(phone -> Phone.builder()
                                .id(UUID.randomUUID())
                                .countrycode(phone.getCountrycode())
                                .number(phone.getNumber())
                                .citycode(phone.getCitycode())
                                .user(user)
                                .build()).collect(Collectors.toList()));
                userRepository.save(user);
            }


            return StatusService.builder().statusServiceEnum(StatusServiceEnum.CREATED)
                    .message(ResultSignUpDto.builder()
                            .id(user.getId())
                            .created(user.getCreated())
                            .isActive(user.isActive())
                            .build()).build();

        } catch (Exception e) {
            throw new ServiceException(Messages.ERROR_UNCONTROLLED);
        }



    }

    public UserDTO loginUser(UUID id) throws ServiceException {
        User user = this.userRepository.getById(id);
        user.setLastLogin(LocalDateTime.now());

        this.userRepository.save(user);

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .isActive(user.isActive())
                .phones(user.getPhones().stream().map(phone -> UserPhoneDto.builder().
                        countrycode(phone.getCountrycode())
                        .citycode(phone.getCitycode())
                        .number(phone.getNumber())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public Optional<UserDTO> getUserByEmail(String email) throws UsernameNotFoundException  {
        Optional<User> userRepositoryByEmail = this.userRepository.findByEmail(email);
        if( userRepositoryByEmail.isPresent()){

            return  userRepositoryByEmail.map(user -> {

                List<UserPhoneDto> phoneDtos= null;
                if(user.getPhones() != null){

                    phoneDtos= user.getPhones().stream().map(phone -> UserPhoneDto.builder().
                            countrycode(phone.getCountrycode())
                            .citycode(phone.getCitycode())
                            .number(phone.getNumber())
                            .build()).collect(Collectors.toList());


                }
                return UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .created(user.getCreated())
                        .lastLogin(user.getLastLogin())
                        .isActive(user.isActive())
                        .phones(phoneDtos)
                        .build();
                    }
            )   ;
        }else{
            throw new UsernameNotFoundException(Messages.DATA_NOT_FOUND);
        }

    }

    public List<UserDTO> getAll() throws ServiceException {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user-> UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .isActive(user.isActive())
                .build()).collect(Collectors.toList());
    }
}
