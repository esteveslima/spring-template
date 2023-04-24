package com.template.spring.demo.core.application.usecases.auth;

import com.template.spring.demo.core.application.exceptions.usecases.auth.UnauthorizedException;
import com.template.spring.demo.core.application.interfaces.auth_token.AuthTokenPayloadDTO;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.auth.LoginStoreUseCaseDTO;
import com.template.spring.demo.core.application.interfaces.ports.hash.HashGateway;
import com.template.spring.demo.core.application.interfaces.ports.token.TokenGateway;
import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import com.template.spring.demo.core.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginStoreUseCase {

    private UserRepository userRepository;
    private StoreRepository storeRepository;
    private HashGateway hashGateway;
    private TokenGateway<AuthTokenPayloadDTO> tokenGateway;

    @Autowired
    public LoginStoreUseCase(
            UserRepository userRepository,
            StoreRepository storeRepository,
            HashGateway hashGateway,
            TokenGateway<AuthTokenPayloadDTO> tokenGateway
    ) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.hashGateway = hashGateway;
        this.tokenGateway = tokenGateway;
    }

    public LoginStoreUseCaseDTO.Result execute(LoginStoreUseCaseDTO.Params params) throws UnauthorizedException {
        UserEntity user = this.userRepository.getUserByUsername(params.username);

        boolean isInputPasswordCorrect = this.hashGateway.compareHash(params.password, user.getEncodedPassword());
        if(!isInputPasswordCorrect){
            throw new UnauthorizedException(params);
        }

        this.verifyUserStore(user.getId());

        AuthTokenPayloadDTO authTokenPayload = new AuthTokenPayloadDTO(user.getId(), AuthTokenPayloadDTO.AuthRoleEnum.STORE);
        String authToken = this.tokenGateway.generateToken(authTokenPayload);

        return new LoginStoreUseCaseDTO.Result(authToken);
    }

    private void verifyUserStore(int userId){
        this.storeRepository.getStoreByUserId(userId);
    }
}
