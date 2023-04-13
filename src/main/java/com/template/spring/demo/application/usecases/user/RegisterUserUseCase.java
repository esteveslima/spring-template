package com.template.spring.demo.application.usecases.user;

import com.template.spring.demo.application.interfaces.dtos.usecases.user.RegisterUserUseCaseDTO;
import com.template.spring.demo.domain.repositories.user.UserGateway;
import com.template.spring.demo.domain.repositories.user.dtos.UserGatewayRegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserGateway userGateway;

    @Autowired
    public RegisterUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public RegisterUserUseCaseDTO.Result execute(RegisterUserUseCaseDTO.Params params) {

        UserGatewayRegisterUserDTO.Params registerUserParametersDTO = new UserGatewayRegisterUserDTO.Params(
                params.username,
                params.email,
                params.password
        );

        UserGatewayRegisterUserDTO.Result registrationResult = this.userGateway.registerUser(registerUserParametersDTO);

        return new RegisterUserUseCaseDTO.Result(
                registrationResult.username,
                registrationResult.email,
                registrationResult.id
        );
    }
}
