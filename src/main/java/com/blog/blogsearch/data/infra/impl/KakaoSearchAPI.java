package com.blog.blogsearch.data.infra.impl;

import com.blog.blogsearch.common.exception.RestAPIException;
import com.blog.blogsearch.common.exception.code.CommonErrorCode;
import com.blog.blogsearch.data.dto.KakaoAPIResponse;
import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.data.infra.SearchAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class KakaoSearchAPI implements SearchAPI {
    @Value("${kakao.key}")
    private String key;

    @Value("${kakao.baseUri}")
    private String baseUri;

    @Value("${kakao.endPoint}")
    private String endPoint;

    @Override
    @Cacheable(value = "searchCacheStore", key = "#request.hashCode()")
    public OpenAPIResponse request(SearchRequestDto request) {
        try {
            String requestUri = makeUri(request);
            HttpHeaders headers = makeHeaders(Map.of("Authorization", "KakaoAK " + key));

            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            KakaoAPIResponse kakaoAPIResponse = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, KakaoAPIResponse.class)
                    .getBody();
            return new OpenAPIResponse(kakaoAPIResponse, request.getPage(), request.getSize());
        } catch (Exception e) {
            throw new RestAPIException(CommonErrorCode.SEARCH_API_EXCEPTION);
        }
    }

    @Override
    public String makeUri(SearchRequestDto request) {
        return UriComponentsBuilder.fromHttpUrl(baseUri + endPoint)
                .queryParam("query", request.getQuery())
                .queryParam("sort", request.getSort())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .encode()
                .toUriString();
    }


}
