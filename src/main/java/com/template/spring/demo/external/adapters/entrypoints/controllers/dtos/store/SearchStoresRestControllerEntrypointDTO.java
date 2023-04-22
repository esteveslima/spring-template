package com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class SearchStoresRestControllerEntrypointDTO {

//    public class Request {
//
//        // Assigning query/path parameters to a POJO is not currently supported naitvelly. For now, moved the fields directly to the controller instead
//        @Data
//        public static class Query {
//            @NotBlank
//            public String searchQuery;
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
            public List<ResultItem> stores;

            @Data
            @AllArgsConstructor
            public static class ResultItem {
                public int id;
                public int userId;
                public String name;
            }
        }
    }

}
