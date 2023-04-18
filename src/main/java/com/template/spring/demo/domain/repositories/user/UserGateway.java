package com.template.spring.demo.domain.repositories.user;

import com.template.spring.demo.domain.exceptions.user.UserAlreadyExistsException;
import com.template.spring.demo.domain.exceptions.user.UserNotFoundException;
import com.template.spring.demo.domain.repositories.user.dtos.UserGatewayGetUserByUsernameDTO;
import com.template.spring.demo.domain.repositories.user.dtos.UserGatewayRegisterUserDTO;

public interface UserGateway {
    public UserGatewayRegisterUserDTO.Result registerUser(UserGatewayRegisterUserDTO.Params params) throws UserAlreadyExistsException;
    public UserGatewayGetUserByUsernameDTO.Result getUserByUsername(UserGatewayGetUserByUsernameDTO.Params params) throws UserNotFoundException;
}
