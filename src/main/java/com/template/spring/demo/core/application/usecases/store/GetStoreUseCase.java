package com.template.spring.demo.core.application.usecases.store;

import com.template.spring.demo.core.application.interfaces.usecases.dtos.store.GetStoreUseCaseDTO;
import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetStoreUseCase {

    private StoreRepository storeRepository;

    @Autowired
    public GetStoreUseCase(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public GetStoreUseCaseDTO.Result execute(GetStoreUseCaseDTO.Params params) {
        StoreEntity store = this.storeRepository.getStoreById(params.id);

        return new GetStoreUseCaseDTO.Result(store.getId(), store.getUser().getId(), store.getName());
    }
}
