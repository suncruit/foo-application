package com.blog.blogsearch.data.dto;

import lombok.Getter;

@Getter
public class Meta {
    private final Long totalCount;
    private final Long pageableCount;
    private final Boolean isEnd;

    public Meta(Long totalCount, Long pageableCount, Boolean isEnd) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.isEnd = isEnd;
    }
}
