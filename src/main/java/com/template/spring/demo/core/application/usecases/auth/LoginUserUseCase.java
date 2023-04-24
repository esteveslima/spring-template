package com.template.spring.demo.core.application.usecases.auth;

import com.template.spring.demo.core.application.interfaces.ports.hash.HashGateway;
import com.template.spring.demo.core.application.exceptions.usecases.auth.UnauthorizedException;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.auth.LoginUserUseCaseDTO;
import com.template.spring.demo.core.application.interfaces.ports.token.TokenGateway;
import com.template.spring.demo.core.application.interfaces.auth_token.AuthTokenPayloadDTO;
import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.repositories.UserRepository;
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

    public LoginUserUseCaseDTO.Result execute(LoginUserUseCaseDTO.Params params) throws UnauthorizedException {
        UserEntity user = this.userRepository.getUserByUsername(params.username);

        boolean isInputPasswordCorrect = this.hashGateway.compareHash(params.password, user.getEncodedPassword());
        if(!isInputPasswordCorrect){
            throw new UnauthorizedException(params);
        }

        AuthTokenPayloadDTO authTokenPayload = new AuthTokenPayloadDTO(user.getId(), AuthTokenPayloadDTO.AuthRoleEnum.USER);
        String authToken = this.tokenGateway.generateToken(authTokenPayload);

        return new LoginUserUseCaseDTO.Result(authToken);
    }
}
