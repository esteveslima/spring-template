package com.template.spring.demo.core.application.usecases.store;

import com.template.spring.demo.core.application.interfaces.dtos.usecases.store.RegisterStoreUseCaseDTO;
import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.entities.UserEntity;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import com.template.spring.demo.core.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterStoreUseCase {

    private StoreRepository storeRepository;
    private UserRepository userRepository;

    @Autowired
    public RegisterStoreUseCase(StoreRepository storeRepository, UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    @Transactional // TODO: fully decouple usecase from DB annotation by implementing the unit of work pattern
    public RegisterStoreUseCaseDTO.Result execute(RegisterStoreUseCaseDTO.Params params) {
        StoreEntity newStore = new StoreEntity();
        // TODO: find a way to deep copy objects using setter methods without having to manually call them, making use of entities built in domain logic
        newStore.setName(params.payload.name);

        UserEntity authUser = this.userRepository.getUserById(params.userId);
        newStore.setUser(authUser);

        StoreEntity registeredStore = this.storeRepository.registerStore(newStore);

        return new RegisterStoreUseCaseDTO.Result(registeredStore.getId(), registeredStore.getUser().getId(), registeredStore.getName());
    }

}
