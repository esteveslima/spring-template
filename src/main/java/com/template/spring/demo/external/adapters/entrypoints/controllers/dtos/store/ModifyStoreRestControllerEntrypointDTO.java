package com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.store;

import com.template.spring.demo.external.infrastructure.annotations.validators.StoreEntityFieldValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ModifyStoreRestControllerEntrypointDTO {

    public class Request {
        @Data
        public static class Body {
            @StoreEntityFieldValidator(fieldName = "name")
            public String name;
        }
    }

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
