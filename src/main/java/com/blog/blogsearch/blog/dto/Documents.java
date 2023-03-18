package com.blog.blogsearch.blog.dto;

public class Documents {
    private final String contents;
    private final String dateTime;
    private final String title;
    private final String url;

    public Documents(String contents, String dateTime, String title, String url) {
        this.contents = contents;
        this.dateTime = dateTime;
        this.title = title;
        this.url = url;
    }

    public String getContents() {
        return contents;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
