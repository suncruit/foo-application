package com.blog.blogsearch.data.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class KakaoAPIResponse {
    private final Meta meta;
    private final List<Document> documents;

    public KakaoAPIResponse(Meta meta, List<Document> documents) {
        this.meta = meta;
        this.documents = documents;
    }
}
