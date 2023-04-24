package com.template.spring.demo.core.application.usecases.product;

import com.template.spring.demo.core.application.interfaces.usecases.dtos.product.GetProductUseCaseDTO;
import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProductUseCase {

    private ProductRepository productRepository;

    @Autowired
    public GetProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public GetProductUseCaseDTO.Result execute(GetProductUseCaseDTO.Params params) {
        ProductEntity product = this.productRepository.getProductById(params.id);

        return new GetProductUseCaseDTO.Result(
                product.getId(),
                product.getStore().getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrency()
        );
    }
}
