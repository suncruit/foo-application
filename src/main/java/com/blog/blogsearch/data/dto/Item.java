package com.blog.blogsearch.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private String postdate;
}
