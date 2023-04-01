package com.blog.blogsearch.data.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverAPIResponse {
    private final int total;
    private final int start;
    private final int display;
    private final List<Item> items;

    public NaverAPIResponse(int total, int start, int display, List<Item> items) {
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }
}
