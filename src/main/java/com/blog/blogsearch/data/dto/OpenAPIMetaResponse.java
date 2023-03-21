package com.blog.blogsearch.data.dto;

import lombok.Getter;

@Getter
public class OpenAPIMetaResponse {
    private final Integer totalCount;
    private final Integer page;
    private final Integer size;

    public OpenAPIMetaResponse(Integer totalCount, Integer page, Integer size) {
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
    }
}
