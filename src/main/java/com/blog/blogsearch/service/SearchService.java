package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.PopularDto;
import com.blog.blogsearch.data.dto.PopularKeyword;
import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        List<SearchEntity> entities = searchRepository.findTop10ByOrderByCountDesc();
        List<PopularKeyword> collect = entities.stream().map(SearchEntity::toPopularKeyword).collect(Collectors.toList());
        return new PopularDto.Response(collect);
    }

}
