package com.template.spring.demo.application.usecases.register_user;

import com.template.spring.demo.domain.repositories.user.UserGateway;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserGateway userGateway;

    @Autowired
    public RegisterUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public RegisterUserUseCaseResultDTO execute(RegisterUserUseCaseParametersDTO params) {

        UserGatewayRegisterUserParametersDTO registerUserParametersDTO = new UserGatewayRegisterUserParametersDTO(
                params.username,
                params.email,
                params.password
        );

        UserGatewayRegisterUserResultDTO registrationResult = this.userGateway.registerUser(registerUserParametersDTO);

        return new RegisterUserUseCaseResultDTO(
                registrationResult.username,
                registrationResult.email,
                registrationResult.id
        );
    }
}
