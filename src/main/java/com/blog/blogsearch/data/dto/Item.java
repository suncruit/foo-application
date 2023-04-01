package com.blog.blogsearch.data.dto;

import lombok.Getter;

@Getter
public class Item {
    private final String title;
    private final String link;
    private final String description;
    private final String bloggername;
    private final String bloggerlink;
    private final String postdate;

    public Item(String title, String link, String description, String bloggername, String bloggerlink, String postdate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.bloggername = bloggername;
        this.bloggerlink = bloggerlink;
        this.postdate = postdate;
    }
}
