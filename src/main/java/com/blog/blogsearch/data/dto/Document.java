package com.blog.blogsearch.data.dto;

import lombok.Getter;


@Getter
public class Document {
    private final String title;
    private final String contents;
    private final String url;
    private final String blogname;
    private final String thumbnail;
    private final String datetime;

    public Document(String title, String contents, String url, String blogname, String thumbnail, String datetime) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.blogname = blogname;
        this.thumbnail = thumbnail;
        this.datetime = datetime;
    }
}