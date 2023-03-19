package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.PopularDto;
import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final SearchAPIService searchAPIService;

    @Transactional
    public SearchDto.Response searchAndIncreaseCount(SearchDto.Request searchDto) {
        SearchEntity searchEntity = searchRepository.findBySearchKeyword(searchDto.getQuery())
                .orElseGet(() -> searchRepository.save(SearchEntity.fromDto(searchDto)));
        searchEntity.increaseCount();
        return searchAPIService.request(searchDto);
    }

    public PopularDto.Response getPopularTopTen() {
        searchRepository.findAll();
        return new PopularDto.Response();
    }

}
