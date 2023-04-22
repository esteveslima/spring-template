package com.template.spring.demo.core.application.usecases.store;

import com.template.spring.demo.core.application.interfaces.dtos.usecases.store.SearchStoresUseCaseDTO;
import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchStoresUseCase {

    private StoreRepository storeRepository;

    @Autowired
    public SearchStoresUseCase(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional // TODO: fully decouple usecase from DB annotation by implementing the unit of work pattern
    public SearchStoresUseCaseDTO.Result execute(SearchStoresUseCaseDTO.Params params) {
        List<StoreEntity> storesEntities = this.storeRepository.searchStores(params.searchQuery, params.limit, params.offset);

        List<SearchStoresUseCaseDTO.Result.ResultItem> storesDTOs = storesEntities.stream().map((storeEntity) ->
                new SearchStoresUseCaseDTO.Result.ResultItem(storeEntity.getId(), storeEntity.getUser().getId(), storeEntity.getName())
        ).toList();

        return new SearchStoresUseCaseDTO.Result(storesDTOs);
    }
}
