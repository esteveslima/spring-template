package com.template.spring.demo.core.domain.entities;

import com.template.spring.demo.core.domain.exceptions.product.ProductFieldFailedValidationException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity @Table(name = "products")  // TODO: Find a way to separate DB annotations from domain entities or ensure a data mapper is stable and performant(had a separated model class, changed ORM annotations to entity to avoid having mappings on repositories)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)  // TODO: apparently lazy loading is not working properly, it's being loaded eagerly for some reason. It's possible to see the data in the debugger and logs
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "stock")
    private int stock;

    public enum CurrencyEnum {
        USD,
        EUR;
    }

    //

    public ProductEntity(StoreEntity store, String name, String description, double price, CurrencyEnum currency, int stock) {
        ProductEntity.validateName(name);
        ProductEntity.validateDescription(description);
        ProductEntity.validatePrice(price);

        this.id = Integer.MIN_VALUE;
        this.store = store;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
    }

    public void setName(String name) throws ProductFieldFailedValidationException {
        ProductEntity.validateName(name);
        this.name = name;
    }

    public void setDescription(String description) throws ProductFieldFailedValidationException {
        ProductEntity.validateDescription(description);
        this.description = description;
    }

    public void setPrice(double price) throws ProductFieldFailedValidationException {
        ProductEntity.validatePrice(price);
        this.price = price;
    }

    public void setStock(int stock) throws ProductFieldFailedValidationException {
        ProductEntity.validateStock(stock);
        this.stock = stock;
    }

    //

    public static void validateName(String name) throws ProductFieldFailedValidationException {
        if(name == null){
            String errorMessage = "Name cannot be null";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("name", name);
            throw new ProductFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        int nameMinLength = 5;
        int nameMaxLength = 100;
        boolean isAdequateLength = name.length() >= nameMinLength && name.length() <= nameMaxLength;
        if(!isAdequateLength){
            String errorMessage = String.format("Name length must be between %d and %d", nameMinLength, nameMaxLength);
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("name", name);
            throw new ProductFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        return;
    }

    public static void validateDescription(String description) throws ProductFieldFailedValidationException {
        if(description == null){
            String errorMessage = "Description cannot be null";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("description", description);
            throw new ProductFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        int descriptionMinLength = 10;
        int descriptionMaxLength = 250;
        boolean isAdequateLength = description.length() >= descriptionMinLength && description.length() <= descriptionMaxLength;
        if(!isAdequateLength){
            String errorMessage = String.format("Description length must be between %d and %d", descriptionMinLength, descriptionMaxLength);
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("description", description);
            throw new ProductFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        return;
    }

    public static void validatePrice(double price) throws ProductFieldFailedValidationException {
        if(price <= 0){
            String errorMessage = "Price must be greater than zero";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("price", price);
            throw new ProductFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        return;
    }

    public static void validateCurrency(String currencyStr) throws ProductFieldFailedValidationException {
        if(currencyStr == null){
            String errorMessage = "Currency cannot be null";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("currencyStr", currencyStr);
            throw new ProductFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        try {
            Enum.valueOf(CurrencyEnum.class, currencyStr);
            return;
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format(
                    "Currency must be one of the values: %s",
                    Arrays.stream(CurrencyEnum.values()).map((enumItem) -> enumItem.name()).toList()
            );
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("currencyStr", currencyStr);
            throw new ProductFieldFailedValidationException(errorMessage, validationFailuresMap);
        }
    }

    public static void validateStock(int stock) throws ProductFieldFailedValidationException {
        if(stock < 0){
            String errorMessage = "Stock must be greater than zero";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("stock", stock);
            throw new ProductFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        return;
    }
}
