package com.template.spring.demo.external.adapters.gateways.databases.repositories;


import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.exceptions.store.StoreAlreadyExistsException;
import com.template.spring.demo.core.domain.exceptions.store.StoreNotFoundException;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreDatabaseRepositoryGateway implements StoreRepository {

    private EntityManager entityManager;

    @Autowired
    public StoreDatabaseRepositoryGateway(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public StoreEntity registerStore(StoreEntity entity) throws StoreAlreadyExistsException {
        int idInsertOperation = 0;
        entity.setId(idInsertOperation);

        try {
            StoreEntity result = this.entityManager.merge(entity);

            return result;
        } catch(PersistenceException exception) {
            throw new StoreAlreadyExistsException(entity, exception);
        }
    }

    @Override
    public StoreEntity getStoreById(int id) throws StoreNotFoundException {
        String jpqlQuery = "SELECT stores FROM StoreEntity stores WHERE stores.id = :idValue";
        TypedQuery<StoreEntity> typedQuery = entityManager.createQuery(jpqlQuery,StoreEntity.class)
                .setParameter("idValue", id);

        try{
            StoreEntity result = typedQuery.getSingleResult();

            return result;
        } catch(NoResultException exception) {
            throw new StoreNotFoundException(Integer.toString(id), exception);
        }
    }

    @Override
    public StoreEntity getStoreByUserId(int id) throws StoreNotFoundException {
        String jpqlQuery = "SELECT stores FROM StoreEntity stores JOIN stores.user users WHERE users.id = :userIdValue";
        TypedQuery<StoreEntity> typedQuery = entityManager.createQuery(jpqlQuery,StoreEntity.class)
                .setParameter("userIdValue", id);

        try{
            StoreEntity result = typedQuery.getSingleResult();

            return result;
        } catch(NoResultException exception) {
            throw new StoreNotFoundException(Integer.toString(id), exception);
        }
    }

    @Override
    public List<StoreEntity> searchStores(String searchQuery, int limit, int offset) {
        if(limit <= 0) { limit = 10; }
        if(offset <= 0) { offset = 0; }

        String searchQueryWithWildcard = "%" + searchQuery + "%";

        String jpqlQuery = "SELECT stores FROM StoreEntity stores WHERE stores.name LIKE :searchQuery";
        TypedQuery<StoreEntity> typedQuery = entityManager.createQuery(jpqlQuery,StoreEntity.class)
                .setParameter("searchQuery", searchQueryWithWildcard)
                .setMaxResults(limit)
                .setFirstResult(offset);

        List<StoreEntity> result = typedQuery.getResultList();

        return result;
    }

    @Override
    public StoreEntity updateStore(StoreEntity entity) throws StoreNotFoundException, StoreAlreadyExistsException {
        try {
            StoreEntity result = this.entityManager.merge(entity);

            return result;
        } catch(NoResultException exception) {
            throw new StoreNotFoundException(Integer.toString(entity.getId()), exception);
        } catch(PersistenceException exception) {
            throw new StoreAlreadyExistsException(entity, exception);
        }
    }
}
