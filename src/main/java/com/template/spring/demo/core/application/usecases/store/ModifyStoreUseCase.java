package com.template.spring.demo.core.application.usecases.store;

import com.template.spring.demo.core.application.interfaces.usecases.dtos.store.ModifyStoreUseCaseDTO;
import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyStoreUseCase {

    private StoreRepository storeRepository;

    @Autowired
    public ModifyStoreUseCase(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional // TODO: fully decouple usecase from DB annotation by implementing the unit of work pattern
    public ModifyStoreUseCaseDTO.Result execute(ModifyStoreUseCaseDTO.Params params) {
        StoreEntity storeToUpdate = this.storeRepository.getStoreByUserId(params.userId);
        // TODO: find a way to fetch in parallel(ComputableFuture) when entities operated by entity manager are in lazy loading mode(without throwing org.hibernate.LazyInitializationException) -> OBS.: for some reason it works when putting transactional in the usecase and all repository methods
        if(params.payload.name != null) storeToUpdate.setName(params.payload.name);

        StoreEntity updatedStore = this.storeRepository.updateStore(storeToUpdate);

        return new ModifyStoreUseCaseDTO.Result(updatedStore.getId(), updatedStore.getUser().getId(), updatedStore.getName());
    }

}
