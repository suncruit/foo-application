package com.blog.blogsearch.data.dto;

import lombok.Getter;

@Getter
public class OpenAPIBlogResponse {
    private final String title;
    private final String contents;
    private final String url;
    private final String blogname;
    private final String thumbnail;
    private final String datetime;

    public OpenAPIBlogResponse(Document document) {
        this.title = document.getTitle();
        this.contents = document.getContents();
        this.url = document.getUrl();
        this.blogname = document.getBlogname();
        this.thumbnail = document.getThumbnail();
        this.datetime = document.getDatetime();
    }

    public OpenAPIBlogResponse(Item item) {
        this.title = item.getTitle();
        this.contents = item.getDescription();
        this.url = item.getLink();
        this.blogname = item.getBloggername();
        this.thumbnail = "";
        this.datetime = item.getPostdate();
    }
}
