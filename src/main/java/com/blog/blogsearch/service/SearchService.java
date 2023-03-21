package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.data.infra.impl.KakaoSearchAPI;
import com.blog.blogsearch.data.infra.impl.NaverSearchAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final KakaoSearchAPI kakaoSearchAPI;
    private final NaverSearchAPI naverSearchAPI;
    private final SearchCountService searchCountService;

    @Transactional
    public OpenAPIResponse searchAndIncreaseCount(SearchRequestDto searchRequestDto) {
        OpenAPIResponse openAPIResponse;
        try {
            openAPIResponse = kakaoSearchAPI.request(searchRequestDto);
        } catch (Exception e) {
            openAPIResponse = naverSearchAPI.request(searchRequestDto);
        }
        searchCountService.increaseCount(searchRequestDto);
        return openAPIResponse;
    }

}
