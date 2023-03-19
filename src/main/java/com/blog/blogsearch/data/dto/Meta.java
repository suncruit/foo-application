package com.blog.blogsearch.data.dto;

import com.blog.blogsearch.data.APISource;
import lombok.Getter;

@Getter
public class Meta {
    private final Long totalCount;
    private final Long pageableCount;
    private final Boolean isEnd;
    private final APISource apiSource;

    public Meta(Long totalCount, Long pageableCount, Boolean isEnd, APISource apiSource) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.isEnd = isEnd;
        this.apiSource = apiSource;
    }
}
