package com.template.spring.demo.core.application.interfaces.usecases.dtos.store;

import lombok.Data;

public class ModifyStoreUseCaseDTO {
    @Data
    public static class Params {
        public final int userId;
        public final Payload payload;

        @Data
        public static class Payload {
            public final String name;
        }
    }

    @Data
    public static class Result {
        public final int id;
        public final int userId;
        public final String name;
    }
}
