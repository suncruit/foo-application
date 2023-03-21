package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.KakaoAPIResponse;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.data.infra.SearchAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchAPIService {

    private final SearchAPI searchAPI;

    @Cacheable(value = "searchCacheStore", key = "#searchRequestDto.hashCode()")
    public KakaoAPIResponse request(SearchRequestDto searchRequestDto) {
        return searchAPI.request(searchRequestDto);
    }
}
