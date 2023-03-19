package com.blog.blogsearch.data.dto;

import lombok.Getter;

@Getter
public class Documents {
    private final String title;
    private final String contents;
    private final String url;
    private final String dateTime;

    public Documents(String contents, String dateTime, String title, String url) {
        this.contents = contents;
        this.dateTime = dateTime;
        this.title = title;
        this.url = url;
    }
}
