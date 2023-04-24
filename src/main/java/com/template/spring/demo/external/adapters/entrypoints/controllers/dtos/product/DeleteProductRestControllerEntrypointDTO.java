package com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.product;

import com.template.spring.demo.core.domain.entities.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

public class DeleteProductRestControllerEntrypointDTO {

//    public class Request {
//
//        // Assigning query/path parameters to a POJO is not currently supported natively. For now, moved the fields directly to the controller instead
//        @Data
//        public static class Path {
//            @NotNull
//            @Size(min = 0)
//            public int id;
//        }
//    }

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
        }
    }

}
