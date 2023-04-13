package com.template.spring.demo.application.usecases.auth;

import com.template.spring.demo.application.interfaces.ports.HashGateway;
import com.template.spring.demo.application.exceptions.auth.LoginUnauthorizedException;
import com.template.spring.demo.application.interfaces.dtos.usecases.auth.LoginUseCaseDTO;
import com.template.spring.demo.domain.repositories.user.UserGateway;
import com.template.spring.demo.domain.repositories.user.dtos.UserGatewayGetUserDTO;
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

    public LoginUseCaseDTO.Result execute(LoginUseCaseDTO.Params params) {
        UserGatewayGetUserDTO.Params getUserParametersDTO = new UserGatewayGetUserDTO.Params(
                params.username
        );

        UserGatewayGetUserDTO.Result userResult = this.userGateway.getUserByUsername(getUserParametersDTO);

        boolean isInputPasswordCorrect = this.hashGateway.compareHash(params.password, userResult.hashPassword);
        if(!isInputPasswordCorrect){
            throw new LoginUnauthorizedException(params);
        }

        String authToken = UUID.randomUUID().toString();
        return new LoginUseCaseDTO.Result(
                authToken
        );
    }
}
