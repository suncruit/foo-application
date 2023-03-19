package com.blog.blogsearch.data.dto;

import lombok.Getter;

@Getter
public class PopularKeyword {

    private final String keyword;

    private final Integer count;

    public PopularKeyword(String keyword, Integer count) {
        this.keyword = keyword;
        this.count = count;
    }
}
