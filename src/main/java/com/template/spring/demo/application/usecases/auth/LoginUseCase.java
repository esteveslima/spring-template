package com.template.spring.demo.application.usecases.auth;

import com.template.spring.demo.application.interfaces.ports.hash.HashGateway;
import com.template.spring.demo.application.exceptions.auth.UnauthorizedException;
import com.template.spring.demo.application.interfaces.dtos.usecases.auth.LoginUseCaseDTO;
import com.template.spring.demo.application.interfaces.ports.token.TokenGateway;
import com.template.spring.demo.application.interfaces.ports.token.TokenPayloadDTO;
import com.template.spring.demo.domain.entities.UserEntity;
import com.template.spring.demo.domain.repositories.user.UserGateway;
import com.template.spring.demo.domain.repositories.user.dtos.UserGatewayGetUserByUsernameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginUseCase {

    private final UserGateway userGateway;
    private HashGateway hashGateway;
    private TokenGateway tokenGateway;

    @Autowired
    public LoginUseCase(UserGateway userGateway, HashGateway hashGateway, TokenGateway tokenGateway) {
        this.userGateway = userGateway;
        this.hashGateway = hashGateway;
        this.tokenGateway = tokenGateway;
    }

    public LoginUseCaseDTO.Result execute(LoginUseCaseDTO.Params params) throws UnauthorizedException {
        UserGatewayGetUserByUsernameDTO.Params getUserParametersDTO = new UserGatewayGetUserByUsernameDTO.Params(
                params.username
        );

        UserGatewayGetUserByUsernameDTO.Result userResult = this.userGateway.getUserByUsername(getUserParametersDTO);

        boolean isInputPasswordCorrect = this.hashGateway.compareHash(params.password, userResult.hashPassword);
        if(!isInputPasswordCorrect){
            throw new UnauthorizedException(params);
        }

        TokenPayloadDTO tokenPayload = new TokenPayloadDTO(
                userResult.username,
                userResult.email,
                UserEntity.EnumUserRole.USER
        );
        String authToken = this.tokenGateway.generateToken(tokenPayload);

        return new LoginUseCaseDTO.Result(
                authToken
        );
    }
}
