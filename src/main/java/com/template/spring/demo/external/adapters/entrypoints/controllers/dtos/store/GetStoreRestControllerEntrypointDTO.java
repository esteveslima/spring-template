package com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.store;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

public class GetStoreRestControllerEntrypointDTO {

//    public class Request {
//
//        // Assigning query/path parameters to a POJO is not currently supported naitvelly. For now, moved the fields directly to the controller instead
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
            public int id;
            public int userId;
            public String name;
        }
    }

}
