package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.PopularKeyword;
import com.blog.blogsearch.data.dto.PopularResponseDto;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
import com.blog.blogsearch.data.infra.impl.KakaoSearchAPI;
import com.blog.blogsearch.data.infra.impl.NaverSearchAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final KakaoSearchAPI kakaoSearchAPI;
    private final NaverSearchAPI naverSearchAPI;

    @Transactional
    public OpenAPIResponse searchAndIncreaseCount(SearchRequestDto searchDto) {
        OpenAPIResponse openAPIResponse;
        try {
            openAPIResponse = kakaoSearchAPI.request(searchDto);
        } catch (Exception e) {
            openAPIResponse = naverSearchAPI.request(searchDto);
        }

        SearchEntity searchEntity = searchRepository.findBySearchKeyword(searchDto.getQuery())
                .orElseGet(() -> searchRepository.save(SearchEntity.fromDto(searchDto)));
        searchEntity.increaseCount();

        return openAPIResponse;
    }

    public PopularResponseDto getPopularTopTen() {
        List<SearchEntity> entities = searchRepository.findTop10ByOrderByCountDesc();
        List<PopularKeyword> collect = entities.stream().map(SearchEntity::toPopularKeyword).collect(Collectors.toList());
        return new PopularResponseDto(collect);
    }

}
