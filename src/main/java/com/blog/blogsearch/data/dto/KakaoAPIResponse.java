package com.blog.blogsearch.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoAPIResponse {
    private Meta meta;
    private List<Document> documents;

}
