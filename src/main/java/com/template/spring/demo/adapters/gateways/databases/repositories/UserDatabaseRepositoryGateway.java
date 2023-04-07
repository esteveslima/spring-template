package com.template.spring.demo.adapters.gateways.databases.repositories;

import com.template.spring.demo.application.ports.HashGateway;
import com.template.spring.demo.domain.exceptions.user.UserAlreadyExistsException;
import com.template.spring.demo.domain.exceptions.user.UserNotFoundException;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.template.spring.demo.domain.entities.UserEntity;
import com.template.spring.demo.domain.repositories.user.UserGateway;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserResultDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDatabaseRepositoryGateway implements UserGateway {

    private Map<String, UserEntity> memoryDb = new HashMap<String, UserEntity>();

    //

    private HashGateway hashGateway;

    @Autowired
    public UserDatabaseRepositoryGateway(HashGateway hashGateway) {
        this.hashGateway = hashGateway;
    }

    @Override
    public UserGatewayRegisterUserResultDTO registerUser(UserGatewayRegisterUserParametersDTO params) throws UserAlreadyExistsException {
        UserEntity userFound = memoryDb.get(params.username);
        boolean isUserFound = userFound != null;
        if(isUserFound){
            throw new UserAlreadyExistsException(params.username);
        }

        int newUserId = memoryDb.size();
        String encodedPassword = this.hashGateway.hashValue(params.password);

        UserEntity newUser = new UserEntity(newUserId, params.username, params.email, encodedPassword);
        memoryDb.put(params.username, newUser);

        return new UserGatewayRegisterUserResultDTO(newUser.id, newUser.username, newUser.email);
    }

    @Override
    public UserGatewayGetUserResultDTO getUser(UserGatewayGetUserParametersDTO params) throws UserNotFoundException {
        UserEntity userFound = memoryDb.get(params.username);

        boolean isUserFound = userFound != null;
        if(!isUserFound){
            throw new UserNotFoundException(params.username);
        }

        return new UserGatewayGetUserResultDTO(
                userFound.id,
                userFound.username,
                userFound.email,
                userFound.password
        );
    }
}
