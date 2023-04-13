package com.template.spring.demo.domain.repositories.user;

import com.template.spring.demo.domain.exceptions.user.UserAlreadyExistsException;
import com.template.spring.demo.domain.exceptions.user.UserNotFoundException;
import com.template.spring.demo.domain.repositories.user.dtos.UserGatewayGetUserDTO;
import com.template.spring.demo.domain.repositories.user.dtos.UserGatewayRegisterUserDTO;

public interface UserGateway {
    public UserGatewayRegisterUserDTO.Result registerUser(UserGatewayRegisterUserDTO.Params params) throws UserAlreadyExistsException;
    public UserGatewayGetUserDTO.Result getUserByUsername(UserGatewayGetUserDTO.Params params) throws UserNotFoundException;
}
