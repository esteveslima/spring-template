package com.template.spring.demo.core.application.usecases.user;

import com.template.spring.demo.core.application.interfaces.dtos.usecases.user.RegisterUserUseCaseDTO;
import com.template.spring.demo.core.application.interfaces.ports.hash.HashGateway;
import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private UserRepository userRepository;
    private HashGateway hashGateway;

    @Autowired
    public RegisterUserUseCase(UserRepository userRepository, HashGateway hashGateway) {
        this.userRepository = userRepository;
        this.hashGateway = hashGateway;
    }

    @Transactional // TODO: fully decouple usecase from DB annotation by implementing the unit of work pattern
    public RegisterUserUseCaseDTO.Result execute(RegisterUserUseCaseDTO.Params params) {
        String encodedPassword = this.hashGateway.hashValue(params.password);
        UserEntity newUser = new UserEntity(params.username, params.email, encodedPassword);

        UserEntity registeredUser = this.userRepository.registerUser(newUser);

        return new RegisterUserUseCaseDTO.Result(registeredUser.username, registeredUser.email, registeredUser.id);
    }
}