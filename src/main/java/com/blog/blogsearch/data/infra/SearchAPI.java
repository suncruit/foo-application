package com.blog.blogsearch.data.infra;

import com.blog.blogsearch.data.dto.KakaoAPIResponse;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;

@Repository
public interface SearchAPI {


    default HttpHeaders makeHeaders(String key, String value) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(key, value);
        return httpHeaders;
    }

    String makeUri(SearchRequestDto request) throws MalformedURLException;

    KakaoAPIResponse request(SearchRequestDto request);
}
