package com.blog.blogsearch.blog.dto;

import lombok.Getter;

import java.util.List;


public class SearchDto {

    @Getter
    public static class Request {
        private final String query;
        private final String sort;
        private final Integer page;
        private final Integer size;


        private static final Integer PAGE_MAX = 50;
        private static final Integer PAGE_MIN = 1;
        private static final Integer SIZE_MAX = 50;
        private static final Integer SIZE_MIN = 1;

        public Request(String query, String sort, Integer page, Integer size) {
            this.query = query;
            this.sort = validateSort(sort);
            this.page = validatePage(page);
            this.size = validateSize(size);
        }

        private Integer validatePage(Integer page) {
            if (page > PAGE_MAX || page < PAGE_MIN) throw new IllegalArgumentException("todo");
            return page;
        }

        private Integer validateSize(Integer size) {
            if (size > SIZE_MAX || size < SIZE_MIN) throw new IllegalArgumentException("todo");
            return size;
        }

        private String validateSort(String sort) {
            if (!SortCondition.fromString(sort)) throw new IllegalArgumentException("todo");
            return sort;
        }
    }

    @Getter
    public static class Response {

        private final Meta meta;
        private final List<Documents> documentsList;

        public Response(Meta meta, List<Documents> documentsList) {
            this.meta = meta;
            this.documentsList = documentsList;
        }
    }

}
