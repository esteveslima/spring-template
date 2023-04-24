package com.template.spring.demo.core.application.usecases.product;

import com.template.spring.demo.core.application.exceptions.usecases.product.ProductNotFromUserStoreException;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.product.ModifyProductUseCaseDTO;
import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.repositories.ProductRepository;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class ModifyProductUseCase {

    private ProductRepository productRepository;
    private StoreRepository storeRepository;

    @Autowired
    public ModifyProductUseCase(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional // TODO: fully decouple usecase from DB annotation by implementing the unit of work pattern
    public ModifyProductUseCaseDTO.Result execute(ModifyProductUseCaseDTO.Params params) {
        // TODO: find a way to fetch in parallel(ComputableFuture) when entities operated by entity manager are in lazy loading mode(without throwing org.hibernate.LazyInitializationException)
        //  // OBS.: for some reason it works when putting transactional in the usecase and all repository methods
        //  // OBS.2: maybe revert the operation, levaing the default as eager loading and explicitely create jpql with lazy laoding(?)
        ProductEntity productToUpdate = this.productRepository.getProductById(params.id);
        StoreEntity userStore = this.storeRepository.getStoreByUserId(params.userId);

        boolean isProductFromUserStore = productToUpdate.getStore().getId() == userStore.getId();
        if(!isProductFromUserStore){
            throw new ProductNotFromUserStoreException(params);
        }

        // TODO: find a way to deep copy objects using setter methods without having to manually call them, making use of entities built in domain logic
        if(params.payload.name != null) productToUpdate.setName(params.payload.name);
        if(params.payload.description != null) productToUpdate.setDescription(params.payload.description);
        if(params.payload.price > 0) productToUpdate.setPrice(params.payload.price);
        if(params.payload.currency != null) productToUpdate.setCurrency(params.payload.currency);

        ProductEntity updatedProduct = this.productRepository.updateProduct(productToUpdate);

        return new ModifyProductUseCaseDTO.Result(
                updatedProduct.getId(),
                updatedProduct.getStore().getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getCurrency()
        );
    }

}
