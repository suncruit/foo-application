package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.PopularDto;
import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.data.infra.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final SearchRepository searchRepository;
    private final SearchService searchService;

    public SearchDto.Response searchAndUpdateCount(SearchDto.Request searchDto) {
        return searchService.request(searchDto);
    }

    public PopularDto.Response getPopularTopTen() {
        searchRepository.findAll();
        return new PopularDto.Response();
    }

}
