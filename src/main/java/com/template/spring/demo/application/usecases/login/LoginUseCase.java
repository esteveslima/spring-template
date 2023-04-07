package com.template.spring.demo.application.usecases.login;

import com.template.spring.demo.application.ports.HashGateway;
import com.template.spring.demo.application.exceptions.auth.LoginUnauthorizedException;
import com.template.spring.demo.domain.repositories.user.UserGateway;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserResultDTO;
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

        UserGatewayGetUserResultDTO userResult = this.userGateway.getUser(getUserParametersDTO);

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
