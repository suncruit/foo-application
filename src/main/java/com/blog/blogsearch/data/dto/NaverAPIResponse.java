package com.blog.blogsearch.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverAPIResponse {
    private int total;
    private int start;
    private int display;
    private List<Item> items;
}
