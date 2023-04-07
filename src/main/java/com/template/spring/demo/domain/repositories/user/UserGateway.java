package com.template.spring.demo.domain.repositories.user;

import com.template.spring.demo.domain.exceptions.user.UserAlreadyExistsException;
import com.template.spring.demo.domain.exceptions.user.UserNotFoundException;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserResultDTO;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserResultDTO;

public interface UserGateway {
    public UserGatewayRegisterUserResultDTO registerUser(UserGatewayRegisterUserParametersDTO params) throws UserAlreadyExistsException;
    public UserGatewayGetUserResultDTO getUser(UserGatewayGetUserParametersDTO params) throws UserNotFoundException;
}
