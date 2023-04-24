package com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.product;

import com.template.spring.demo.core.domain.entities.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class SearchProductsRestControllerEntrypointDTO {

//    public class Request {
//
//        // Assigning query/path parameters to a POJO is not currently supported natively. For now, moved the fields directly to the controller instead
//        @Data
//        public static class Query {
//            @NotBlank
//            public String searchQuery;
//
//            @NotNull
//            public String storeId;
//
//            @NotNull
//            @Size(min = 1, max = 10)
//            public int limit;
//
//            @NotNull
//            @Size(min = 0, max = 10)
//            public int offset;
//        }
//    }

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public List<ResultItem> products;

            @Data
            @AllArgsConstructor
            public static class ResultItem {
                public final int id;
                public final int storeId;
                public final String name;
                public final String description;
                public final double price;
                public final ProductEntity.CurrencyEnum currency;
            }
        }
    }

}
