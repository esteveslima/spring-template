package com.template.spring.demo.core.application.usecases.product;

import com.template.spring.demo.core.application.exceptions.usecases.product.ProductNotFromUserStoreException;
import com.template.spring.demo.core.application.interfaces.usecases.dtos.product.DeleteProductUseCaseDTO;
import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.repositories.ProductRepository;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class DeleteProductUseCase {

    private ProductRepository productRepository;
    private StoreRepository storeRepository;

    @Autowired
    public DeleteProductUseCase(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional // TODO: fully decouple usecase from DB annotation by implementing the unit of work pattern
    public DeleteProductUseCaseDTO.Result execute(DeleteProductUseCaseDTO.Params params) {
        // TODO: find a way to fetch in parallel(ComputableFuture) when entities operated by entity manager are in lazy loading mode(without throwing org.hibernate.LazyInitializationException)
        //  // OBS.: for some reason it works when putting transactional in the usecase and all repository methods
        //  // OBS.2: maybe revert the operation, levaing the default as eager loading and explicitely create jpql with lazy laoding(?)
        ProductEntity productToDelete = this.productRepository.getProductById(params.id);
        StoreEntity userStore = this.storeRepository.getStoreByUserId(params.userId);

        boolean isProductFromUserStore = productToDelete.getStore().getId() == userStore.getId();
        if(!isProductFromUserStore){
            throw new ProductNotFromUserStoreException(params);
        }

        ProductEntity deletedProduct = this.productRepository.deleteProduct(productToDelete);

        return new DeleteProductUseCaseDTO.Result(
                deletedProduct.getId(),
                deletedProduct.getStore().getId(),
                deletedProduct.getName(),
                deletedProduct.getDescription(),
                deletedProduct.getPrice(),
                deletedProduct.getCurrency(),
                deletedProduct.getStock()
        );
    }

}
