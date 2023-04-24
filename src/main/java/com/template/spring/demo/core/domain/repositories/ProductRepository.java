package com.template.spring.demo.core.domain.repositories;

import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.core.domain.exceptions.product.ProductNotFoundException;

import java.util.List;

public interface ProductRepository {
    public ProductEntity registerProduct(ProductEntity entity);
    public ProductEntity getProductById(int id) throws ProductNotFoundException;
    public List<ProductEntity> searchProducts(String searchQuery, int storeId, int limit, int offset);
    public ProductEntity updateProduct(ProductEntity entity) throws ProductNotFoundException;
    public ProductEntity deleteProduct(ProductEntity entity) throws ProductNotFoundException;
}
