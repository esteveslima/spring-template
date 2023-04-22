package com.template.spring.demo.core.domain.repositories;

import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.exceptions.user.UserNotFoundException;
import com.template.spring.demo.core.domain.exceptions.user.UserAlreadyExistsException;

public interface UserRepository {
    public UserEntity registerUser(UserEntity entity) throws UserAlreadyExistsException;
    public UserEntity getUserById(int id) throws UserNotFoundException;
    public UserEntity getUserByUsername(String username) throws UserNotFoundException;
}
