package com.template.spring.demo.core.application.interfaces.dtos.usecases.store;

import lombok.Data;

public class GetStoreUseCaseDTO {
    @Data
    public static class Params {
        public final int id;
    }

    @Data
    public static class Result {
        public final int id;
        public final int userId;
        public final String name;
    }
}
