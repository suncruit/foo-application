package com.blog.blogsearch.data.dto;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OpenAPIResponse {

    private final OpenAPIMetaResponse metadata;
    private final List<OpenAPIBlogResponse> documents;

    public OpenAPIResponse(KakaoAPIResponse kakaoAPIResponse, Integer page, Integer size) {
        Meta meta = kakaoAPIResponse.getMeta();
        this.metadata = new OpenAPIMetaResponse(meta.getTotal_count(), page, size);
        this.documents = kakaoAPIResponse.getDocuments().stream()
                .map(OpenAPIBlogResponse::new)
                .collect(Collectors.toList());
    }

    public OpenAPIResponse(NaverAPIResponse naverAPIResponse) {
        this.metadata = new OpenAPIMetaResponse(
                naverAPIResponse.getTotal(),
                naverAPIResponse.getStart(),
                naverAPIResponse.getDisplay()
        );
        this.documents = naverAPIResponse.getItems()
                .stream()
                .map(OpenAPIBlogResponse::new)
                .collect(Collectors.toList());
    }
}
