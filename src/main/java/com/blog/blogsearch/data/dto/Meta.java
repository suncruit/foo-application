package com.blog.blogsearch.data.dto;

import lombok.Getter;

@Getter
public class Meta {
    private final int total_count;
    private final int pageable_count;
    private final boolean is_end;

    public Meta(int total_count, int pageable_count, boolean is_end) {
        this.total_count = total_count;
        this.pageable_count = pageable_count;
        this.is_end = is_end;
    }
}
