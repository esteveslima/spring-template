package com.template.spring.demo.core.application.usecases.store;

import com.template.spring.demo.core.application.interfaces.dtos.usecases.store.ModifyStoreUseCaseDTO;
import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import com.template.spring.demo.core.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyStoreUseCase {

    private StoreRepository storeRepository;
    private UserRepository userRepository;

    @Autowired
    public ModifyStoreUseCase(StoreRepository storeRepository, UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    @Transactional // TODO: fully decouple usecase from DB annotation by implementing the unit of work pattern
    public ModifyStoreUseCaseDTO.Result execute(ModifyStoreUseCaseDTO.Params params) {
        StoreEntity storeToUpdate = this.storeRepository.getStoreByUserId(params.userId);
        // TODO: find a way to deep copy objects using setter methods without having to manually call them, making use of entities built in domain logic
        storeToUpdate.setName(params.payload.name);


        StoreEntity updatedStore = this.storeRepository.updateStore(storeToUpdate);

        return new ModifyStoreUseCaseDTO.Result(updatedStore.getId(), updatedStore.getUser().getId(), updatedStore.getName());
    }

}
