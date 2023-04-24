package com.template.spring.demo.external.adapters.gateways.databases.repositories;

import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.exceptions.user.UserAlreadyExistsException;
import com.template.spring.demo.core.domain.exceptions.user.UserNotFoundException;
import com.template.spring.demo.core.domain.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDatabaseRepositoryGateway implements UserRepository {

    private EntityManager entityManager;

    @Autowired
    public UserDatabaseRepositoryGateway(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserEntity registerUser(UserEntity entity) throws UserAlreadyExistsException {
        int idInsertOperation = 0;
        entity.setId(idInsertOperation);

        try {
            UserEntity result = this.entityManager.merge(entity);

            return result;
        } catch(PersistenceException exception) {
            throw new UserAlreadyExistsException(entity, exception);
        }
    }

    @Override
    public UserEntity getUserById(int id) throws UserNotFoundException {
        String jpqlQuery = "SELECT users FROM UserEntity users WHERE users.id = :idValue";

        TypedQuery<UserEntity> typedQuery = entityManager.createQuery(jpqlQuery,UserEntity.class)
                .setParameter("idValue", id);

        try{
            UserEntity result = typedQuery.getSingleResult();

            return result;
        } catch(NoResultException exception) {
            throw new UserNotFoundException(Integer.toString(id), exception);
        }
    }

    @Override
    public UserEntity getUserByUsername(String username) throws UserNotFoundException {
        String jpqlQuery = "SELECT users FROM UserEntity users WHERE users.username = :usernameValue";

        TypedQuery<UserEntity> typedQuery = entityManager.createQuery(jpqlQuery,UserEntity.class)
                .setParameter("usernameValue", username);

        try{
            UserEntity result = typedQuery.getSingleResult();

            return result;
        } catch(NoResultException exception) {
            throw new UserNotFoundException(username, exception);
        }
    }
}
