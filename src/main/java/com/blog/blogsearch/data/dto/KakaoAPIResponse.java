package com.blog.blogsearch.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class KakaoAPIResponse {
    private Meta meta;
    private List<Document> documents;

}
