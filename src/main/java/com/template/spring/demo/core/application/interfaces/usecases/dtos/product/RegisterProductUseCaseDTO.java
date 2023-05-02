package com.template.spring.demo.core.application.interfaces.usecases.dtos.product;

import com.template.spring.demo.core.domain.entities.ProductEntity;
import lombok.Data;

public class RegisterProductUseCaseDTO {
    @Data
    public static class Params {
        public final int userId;
        public final Payload payload;

        @Data
        public static class Payload {
            public final String name;
            public final String description;
            public final double price;
            public final ProductEntity.CurrencyEnum currency;
            public final int stock;
        }
    }

    @Data
    public static class Result {
        public final int id;
        public final int storeId;
        public final String name;
        public final String description;
        public final double price;
        public final ProductEntity.CurrencyEnum currency;
        public final int stock;
    }
}
