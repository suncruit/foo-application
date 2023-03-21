package com.blog.blogsearch.data.infra.impl;

import com.blog.blogsearch.data.dto.NaverAPIResponse;
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
public class NaverSearchAPI implements SearchAPI {

    @Value("${naver.id}")
    private String id;

    @Value("${naver.secret}")
    private String secret;

    @Value("${naver.baseUri}")
    private String baseUri;

    @Value("${naver.endPoint}")
    private String endPoint;

    @Override
    @Cacheable(value = "searchCacheStore", key = "#request.hashCode()")
    public OpenAPIResponse request(SearchRequestDto request) {
        String requestUri = makeUri(request);
        HttpHeaders headers = makeHeaders(Map.of("X-Naver-Client-Id", id, "X-Naver-Client-Secret", secret));
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        NaverAPIResponse naverAPIResponse = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, NaverAPIResponse.class)
                .getBody();
        return new OpenAPIResponse(naverAPIResponse);
    }

    @Override
    public String makeUri(SearchRequestDto request) {
        String sort = request.getSort().equals("accuracy") ? "sim" : "date";
        return UriComponentsBuilder.fromHttpUrl(baseUri + endPoint)
                .queryParam("query", request.getQuery())
                .queryParam("sort", sort)
                .queryParam("start", request.getPage())
                .queryParam("display", request.getSize())
                .encode()
                .toUriString();
    }
}
