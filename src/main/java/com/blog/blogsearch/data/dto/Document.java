package com.blog.blogsearch.data.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Document {
    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private String datetime;
}