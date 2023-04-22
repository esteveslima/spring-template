package com.template.spring.demo.core.application.usecases.auth;

import com.template.spring.demo.core.application.interfaces.ports.hash.HashGateway;
import com.template.spring.demo.core.application.exceptions.auth.UnauthorizedException;
import com.template.spring.demo.core.application.interfaces.dtos.usecases.auth.LoginUseCaseDTO;
import com.template.spring.demo.core.application.interfaces.ports.token.TokenGateway;
import com.template.spring.demo.core.application.interfaces.auth.AuthTokenPayloadDTO;
import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginUserUseCase {

    private UserRepository userRepository;
    private HashGateway hashGateway;
    private TokenGateway<AuthTokenPayloadDTO> tokenGateway;

    @Autowired
    public LoginUserUseCase(UserRepository userRepository, HashGateway hashGateway, TokenGateway<AuthTokenPayloadDTO> tokenGateway) {
        this.userRepository = userRepository;
        this.hashGateway = hashGateway;
        this.tokenGateway = tokenGateway;
    }

    public LoginUseCaseDTO.Result execute(LoginUseCaseDTO.Params params) throws UnauthorizedException {
        UserEntity user = this.userRepository.getUserByUsername(params.username);

        boolean isInputPasswordCorrect = this.hashGateway.compareHash(params.password, user.encodedPassword);
        if(!isInputPasswordCorrect){
            throw new UnauthorizedException(params);
        }

        AuthTokenPayloadDTO authTokenPayload = new AuthTokenPayloadDTO(user.id, AuthTokenPayloadDTO.AuthRoleEnum.USER);
        String authToken = this.tokenGateway.generateToken(authTokenPayload);

        return new LoginUseCaseDTO.Result(authToken);
    }
}
