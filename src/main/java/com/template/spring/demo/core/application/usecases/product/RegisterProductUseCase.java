package com.template.spring.demo.core.application.usecases.product;

import com.template.spring.demo.core.application.interfaces.usecases.dtos.product.RegisterProductUseCaseDTO;
import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.entities.StoreEntity;
import com.template.spring.demo.core.domain.repositories.ProductRepository;
import com.template.spring.demo.core.domain.repositories.StoreRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterProductUseCase {

    private ProductRepository productRepository;
    private StoreRepository storeRepository;

    @Autowired
    public RegisterProductUseCase(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional // TODO: fully decouple usecase from DB annotation by implementing the unit of work pattern
    public RegisterProductUseCaseDTO.Result execute(RegisterProductUseCaseDTO.Params params) {
        ProductEntity newProduct = new ProductEntity();
        // TODO: find a way to deep copy objects using setter methods without having to manually call them, making use of entities built in domain logic
        newProduct.setName(params.payload.name);
        newProduct.setDescription(params.payload.description);
        newProduct.setPrice(params.payload.price);
        newProduct.setCurrency(params.payload.currency);
        newProduct.setStock(params.payload.stock);

        StoreEntity userStore = this.storeRepository.getStoreByUserId(params.userId);
        newProduct.setStore(userStore);

        ProductEntity registeredProduct = this.productRepository.registerProduct(newProduct);

        return new RegisterProductUseCaseDTO.Result(
                registeredProduct.getId(),
                registeredProduct.getStore().getId(),
                registeredProduct.getName(),
                registeredProduct.getDescription(),
                registeredProduct.getPrice(),
                registeredProduct.getCurrency(),
                registeredProduct.getStock()
        );
    }

}
