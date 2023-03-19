package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.data.infra.SearchAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchAPIService {

    private final SearchAPI searchAPI;

    @Cacheable(value = "searchCacheStore", key = "#searchDto.hashCode()")
    public SearchDto.Response request(SearchDto.Request searchDto) {
        return searchAPI.request(searchDto);
    }
}
