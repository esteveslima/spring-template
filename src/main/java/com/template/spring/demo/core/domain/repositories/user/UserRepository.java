package com.template.spring.demo.core.domain.repositories.user;

import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.exceptions.user.UserNotFoundException;
import com.template.spring.demo.core.domain.exceptions.user.UserAlreadyExistsException;

public interface UserRepository {
    public UserEntity registerUser(UserEntity user) throws UserAlreadyExistsException;
    public UserEntity getUserByUsername(String username) throws UserNotFoundException;
}
