package com.template.spring.demo.core.application.interfaces.usecases.dtos.product;

import com.template.spring.demo.core.domain.entities.ProductEntity;
import lombok.Data;

import java.util.List;

public class SearchProductsUseCaseDTO {
    @Data
    public static class Params {
        public final String searchQuery;
        public final int storeId;
        public final int limit;
        public final int offset;
    }

    @Data
    public static class Result {
        public final List<ResultItem> products;

        @Data
        public static class ResultItem {
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
