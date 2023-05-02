package com.template.spring.demo.core.application.usecases.product;

import com.template.spring.demo.core.application.interfaces.usecases.dtos.product.SearchProductsUseCaseDTO;
import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchProductsUseCase {

    private ProductRepository productRepository;

    @Autowired
    public SearchProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public SearchProductsUseCaseDTO.Result execute(SearchProductsUseCaseDTO.Params params) {
        List<ProductEntity> productsEntities = this.productRepository.searchProducts(
                null, //params.searchQuery,
                params.storeId,
                params.limit,
                params.offset
        );

        List<SearchProductsUseCaseDTO.Result.ResultItem> productsDTOs = productsEntities.stream().map((productEntity) ->
                new SearchProductsUseCaseDTO.Result.ResultItem(
                        productEntity.getId(),
                        productEntity.getStore().getId(),
                        productEntity.getName(),
                        productEntity.getDescription(),
                        productEntity.getPrice(),
                        productEntity.getCurrency(),
                        productEntity.getStock()
                )
        ).toList();

        return new SearchProductsUseCaseDTO.Result(productsDTOs);
    }
}
