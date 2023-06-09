package com.blog.blogsearch.data.infra.impl;

import com.blog.blogsearch.common.exception.RestAPIException;
import com.blog.blogsearch.common.exception.code.CommonErrorCode;
import com.blog.blogsearch.data.dto.KakaoAPIResponse;
import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.service.SearchAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Order(1)
@Component
public class KakaoSearchAPI implements SearchAPI {

    private final RestTemplate restTemplate;

    @Value("${kakao.key}")
    private String key;

    @Value("${kakao.baseUri}")
    private String baseUri;

    @Value("${kakao.endPoint}")
    private String endPoint;

    public KakaoSearchAPI(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    @Cacheable(value = "searchCacheStore", key = "#request.hashCode()")
    public OpenAPIResponse request(SearchRequestDto request) {
        try {
            URI requestUri = makeUri(request);
            HttpHeaders headers = makeHeaders(Map.of("Authorization", "KakaoAK " + key));

            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            KakaoAPIResponse kakaoAPIResponse = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, KakaoAPIResponse.class)
                    .getBody();
            return new OpenAPIResponse(kakaoAPIResponse, request.getPage(), request.getSize());
        } catch (Exception e) {
            throw new RestAPIException(CommonErrorCode.SEARCH_API_EXCEPTION);
        }
    }

    @Override
    public URI makeUri(SearchRequestDto request) {
        return UriComponentsBuilder.fromHttpUrl(baseUri + endPoint)
                .queryParam("query", request.getQuery())
                .queryParam("sort", request.getSort())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .build().encode().toUri();
    }


}
