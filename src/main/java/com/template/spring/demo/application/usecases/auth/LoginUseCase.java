package com.template.spring.demo.application.usecases.auth;

import com.template.spring.demo.application.interfaces.ports.HashGateway;
import com.template.spring.demo.application.exceptions.auth.LoginUnauthorizedException;
import com.template.spring.demo.application.interfaces.usecases.auth.login.LoginUseCaseParametersDTO;
import com.template.spring.demo.application.interfaces.usecases.auth.login.LoginUseCaseResultDTO;
import com.template.spring.demo.domain.repositories.user.UserGateway;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserResultDTO;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginUseCase {

    private final UserGateway userGateway;
    private HashGateway hashGateway;

    @Autowired
    public LoginUseCase(UserGateway userGateway, HashGateway hashGateway) {
        this.userGateway = userGateway;
        this.hashGateway = hashGateway;
    }

    public LoginUseCaseResultDTO execute(LoginUseCaseParametersDTO params) {
        UserGatewayGetUserParametersDTO getUserParametersDTO = new UserGatewayGetUserParametersDTO(
                params.username
        );

        UserGatewayGetUserResultDTO userResult = this.userGateway.getUserByUsername(getUserParametersDTO);

        boolean isInputPasswordCorrect = this.hashGateway.compareHash(params.password, userResult.hashPassword);
        if(!isInputPasswordCorrect){
            throw new LoginUnauthorizedException(params);
        }

        String authToken = UUID.randomUUID().toString();
        return new LoginUseCaseResultDTO(
                authToken
        );
    }
}
