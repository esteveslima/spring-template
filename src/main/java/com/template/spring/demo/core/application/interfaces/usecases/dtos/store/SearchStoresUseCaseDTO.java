package com.template.spring.demo.core.application.interfaces.usecases.dtos.store;

import com.template.spring.demo.core.domain.entities.StoreEntity;
import lombok.Data;

import java.util.List;

public class SearchStoresUseCaseDTO {
    @Data
    public static class Params {
        public final String searchQuery;
        public final int limit;
        public final int offset;
    }

    @Data
    public static class Result {
        public final List<ResultItem> stores;

        @Data
        public static class ResultItem {
            public final int id;
            public final int userId;
            public final String name;
        }
    }
}
