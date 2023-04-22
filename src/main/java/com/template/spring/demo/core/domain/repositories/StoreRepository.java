package com.template.spring.demo.core.domain.repositories;

import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.exceptions.store.StoreAlreadyExistsException;
import com.template.spring.demo.core.domain.exceptions.store.StoreNotFoundException;

import java.util.List;

public interface StoreRepository {
    public StoreEntity registerStore(StoreEntity entity) throws StoreAlreadyExistsException;
    public StoreEntity getStoreById(int id) throws StoreNotFoundException;
    public StoreEntity getStoreByUserId(int id) throws StoreNotFoundException;
    public List<StoreEntity> searchStores(String searchQuery, int limit, int offset);
    public StoreEntity updateStore(StoreEntity entity) throws StoreNotFoundException, StoreAlreadyExistsException;
}
