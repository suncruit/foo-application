package com.blog.blogsearch.service;

import com.blog.blogsearch.common.exception.RestAPIException;
import com.blog.blogsearch.common.exception.code.CommonErrorCode;
import com.blog.blogsearch.data.dto.KakaoAPIResponse;
import com.blog.blogsearch.data.dto.PopularKeyword;
import com.blog.blogsearch.data.dto.PopularResponseDto;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
import com.blog.blogsearch.data.infra.impl.KakaoSearchAPI;
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
//    private final NaverSearchAPI naverSearchAPI;

    @Transactional
    public KakaoAPIResponse searchAndIncreaseCount(SearchRequestDto searchDto) {
        try {
            return kakaoSearchAPI.request(searchDto);
        } catch (RestAPIException e) {
            throw new RestAPIException(CommonErrorCode.SEARCH_API_EXCEPTION);
        }

//        SearchEntity searchEntity = searchRepository.findBySearchKeyword(searchDto.getQuery())
//                .orElseGet(() -> searchRepository.save(SearchEntity.fromDto(searchDto)));
//        searchEntity.increaseCount();
//
//        return apiResponse;
    }

    public PopularResponseDto getPopularTopTen() {
        List<SearchEntity> entities = searchRepository.findTop10ByOrderByCountDesc();
        List<PopularKeyword> collect = entities.stream().map(SearchEntity::toPopularKeyword).collect(Collectors.toList());
        return new PopularResponseDto(collect);
    }

}
