package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.PopularKeyword;
import com.blog.blogsearch.data.dto.PopularResponseDto;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchCountService {

    private final SearchRepository searchRepository;

    @Transactional
    public void increaseCount(SearchRequestDto searchRequestDto) {
        SearchEntity searchEntity = searchRepository.findBySearchKeyword(searchRequestDto.getQuery())
                .orElseGet(() -> searchRepository.save(SearchEntity.fromDto(searchRequestDto)));
        searchEntity.increaseCount();
    }

    public PopularResponseDto getPopularTopTen() {
        List<SearchEntity> entities = searchRepository.findTop10ByOrderByCountDesc();
        List<PopularKeyword> collect = entities.stream().map(SearchEntity::toPopularKeyword).collect(Collectors.toList());
        return new PopularResponseDto(collect);
    }
}
