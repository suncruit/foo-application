package com.blog.blogsearch.data.infra;

import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

public interface SearchAPI {

    default HttpHeaders makeHeaders(Map<String, String> params) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        for (String key : params.keySet()) {
            String value = params.get(key);
            httpHeaders.set(key, value);
        }
        return httpHeaders;
    }

    URI makeUri(SearchRequestDto request) throws MalformedURLException;

    OpenAPIResponse request(SearchRequestDto request);
}
