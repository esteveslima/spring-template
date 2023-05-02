package com.template.spring.demo.external.adapters.gateways.databases.repositories;


import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.exceptions.product.ProductNotFoundException;
import com.template.spring.demo.core.domain.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDatabaseRepositoryGateway implements ProductRepository {

    private EntityManager entityManager;

    @Autowired
    public ProductDatabaseRepositoryGateway(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ProductEntity registerProduct(ProductEntity entity) {
        int idInsertOperation = 0;
        entity.setId(idInsertOperation);

        ProductEntity result = this.entityManager.merge(entity);

        return result;
    }

    @Override
    public ProductEntity getProductById(int id) throws ProductNotFoundException {
        String jpqlQuery = "SELECT products FROM ProductEntity products WHERE products.id = :idValue";

        TypedQuery<ProductEntity> typedQuery = entityManager.createQuery(jpqlQuery,ProductEntity.class)
                .setParameter("idValue", id);

        try{
            ProductEntity result = typedQuery.getSingleResult();
            System.out.println(result.getStore().getName());
            System.out.println(result.getCurrency());
            System.out.println(result.getPrice());
            return result;
        } catch(NoResultException exception) {
            throw new ProductNotFoundException(Integer.toString(id), exception);
        }
    }

    @Override
    public List<ProductEntity> searchProducts(String searchQuery, int storeId, int limit, int offset) {
        if(limit <= 0) { limit = 10; }
        if(offset <= 0) { offset = 0; }

        TypedQuery<ProductEntity> typedQuery;

        boolean isStoreScopeSearch = storeId > 0;
        if(isStoreScopeSearch){
            String jpqlQuery = "SELECT products FROM ProductEntity products JOIN products.store stores WHERE stores.id = :storeIdValue AND products.name LIKE :searchQuery";

            String searchQueryWithWildcard = "%" + searchQuery + "%";

            typedQuery = entityManager.createQuery(jpqlQuery,ProductEntity.class)
                    .setParameter("searchQuery", searchQueryWithWildcard)
                    .setParameter("storeIdValue", storeId)
                    .setMaxResults(limit)
                    .setFirstResult(offset);
        } else {
            String jpqlQuery = "SELECT products FROM ProductEntity products WHERE products.name LIKE :searchQuery";

            String searchQueryWithWildcard = "%" + searchQuery + "%";

            typedQuery = entityManager.createQuery(jpqlQuery,ProductEntity.class)
                    .setParameter("searchQuery", searchQueryWithWildcard)
                    .setMaxResults(limit)
                    .setFirstResult(offset);
        }

        List<ProductEntity> result = typedQuery.getResultList();

        return result;
    }

    @Override
    public ProductEntity updateProduct(ProductEntity entity) throws ProductNotFoundException {
        try {
            ProductEntity result = this.entityManager.merge(entity);

            return result;
        } catch(NoResultException exception) {
            throw new ProductNotFoundException(Integer.toString(entity.getId()), exception);
        }
    }

    @Override
    public ProductEntity deleteProduct(ProductEntity entity) throws ProductNotFoundException {
        try {
            this.entityManager.remove(entity);

            return entity;
        } catch(Exception exception) {
            throw exception;
        }
    }
}
