package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.data.infra.SearchAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final List<SearchAPI> searchAPIList;
    private final SearchCountService searchCountService;

    @Transactional
    public OpenAPIResponse searchAndIncreaseCount(SearchRequestDto searchRequestDto) throws Exception {
        OpenAPIResponse openAPIResponse;
        try {
            openAPIResponse = searchAPIList.get(0).request(searchRequestDto);
        } catch (Exception e) {
            openAPIResponse = searchAPIList.get(1).request(searchRequestDto);
        }
        searchCountService.increaseCount(searchRequestDto);
        return openAPIResponse;
    }

}
