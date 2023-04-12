package com.template.spring.demo.adapters.gateways.databases.repositories;

import com.template.spring.demo.adapters.gateways.databases.models.UserEntityDatabaseModel;
import com.template.spring.demo.application.interfaces.ports.HashGateway;
import com.template.spring.demo.domain.exceptions.user.UserAlreadyExistsException;
import com.template.spring.demo.domain.exceptions.user.UserNotFoundException;
import com.template.spring.demo.domain.repositories.user.UserGateway;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.get_user.UserGatewayGetUserResultDTO;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserParametersDTO;
import com.template.spring.demo.domain.repositories.user.register_user.UserGatewayRegisterUserResultDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDatabaseRepositoryGateway implements UserGateway  {

    private EntityManager entityManager;
    private HashGateway hashGateway;

    @Autowired
    public UserDatabaseRepositoryGateway(EntityManager entityManager, HashGateway hashGateway) {
        this.entityManager = entityManager;
        this.hashGateway = hashGateway;
    }

    @Override
    @Transactional
    public UserGatewayRegisterUserResultDTO registerUser(UserGatewayRegisterUserParametersDTO params) throws UserAlreadyExistsException {

        int idInsertOperation = 0;
        String encodedPassword = this.hashGateway.hashValue(params.password);
        UserEntityDatabaseModel insertObj = new UserEntityDatabaseModel(
                idInsertOperation,
                params.username,
                params.email,
                encodedPassword
        );

        try {
            UserEntityDatabaseModel resultObj = this.entityManager.merge(insertObj);

            return new UserGatewayRegisterUserResultDTO(
                    resultObj.id,
                    resultObj.username,
                    resultObj.email
            );
        } catch(PersistenceException exception) {
            throw new UserAlreadyExistsException(params.username);
        }
    }

    @Override
    public UserGatewayGetUserResultDTO getUserByUsername(UserGatewayGetUserParametersDTO params) throws UserNotFoundException {
        TypedQuery<UserEntityDatabaseModel> query = entityManager.createQuery(
                "SELECT users FROM UserEntityDatabaseModel as users WHERE users.username = :usernameValue",
                UserEntityDatabaseModel.class
        ).setParameter("usernameValue", params.username);

        try{
            UserEntityDatabaseModel resultObj = query.getSingleResult();

            return new UserGatewayGetUserResultDTO(
                    resultObj.id,
                    resultObj.username,
                    resultObj.email,
                    resultObj.password
            );
        } catch(NoResultException exception) {
            throw new UserNotFoundException(params.username);
        }
    }
}
