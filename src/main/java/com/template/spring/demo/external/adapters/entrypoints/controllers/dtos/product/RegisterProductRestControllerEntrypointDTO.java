package com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.product;

import com.template.spring.demo.core.domain.entities.ProductEntity;
import com.template.spring.demo.external.infrastructure.annotations.validators.ProductEntityFieldValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

public class RegisterProductRestControllerEntrypointDTO {

    public static class Request {
        @Data
        public static class Body {
            @ProductEntityFieldValidator(fieldName = "name")
            public final String name;

            @ProductEntityFieldValidator(fieldName = "description")
            public final String description;

            @ProductEntityFieldValidator(fieldName = "price")
            public final double price;

            @ProductEntityFieldValidator(fieldName = "currency")
            public final String currency;

            @ProductEntityFieldValidator(fieldName = "stock")
            public final int stock;
        }
    }

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public final int id;
            public final int storeId;
            public final String name;
            public final String description;
            public final double price;
            public final ProductEntity.CurrencyEnum currency;
            public final int stock;
        }
    }

}
