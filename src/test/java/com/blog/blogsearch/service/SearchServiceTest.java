package com.blog.blogsearch.service;


import com.blog.blogsearch.data.dto.*;
import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
import com.blog.blogsearch.data.infra.impl.KakaoSearchAPI;
import com.blog.blogsearch.data.infra.impl.NaverSearchAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
//@Import
@ContextConfiguration(classes = {SearchRepository.class, SearchService.class, KakaoSearchAPI.class, NaverSearchAPI.class})
public class SearchServiceTest {

    @MockBean
    SearchRepository searchRepository;

    @MockBean
    KakaoSearchAPI kakaoSearchAPI;
    @Autowired
    SearchService searchService;

    @Test
    @DisplayName("검색 서비스 테스트 - findBySearchKeyword()")
    public void findBySearchKeywordTest() {
        //given
        SearchRequestDto requestDto = new SearchRequestDto("자바스크립트", "accuracy", 1, 1);
        SearchEntity searchEntity = new SearchEntity(requestDto.getQuery(), 15);

        Meta meta = new Meta(10, 10, false);
        List<Document> documentList = new ArrayList<>(List.of(new Document("자바스크립트", "contents", "https://foo.com", "blog", "thum", "2023-03-11T00:00:00.000+09:00")));
        OpenAPIResponse response = new OpenAPIResponse(new KakaoAPIResponse(meta, documentList), 1, 1);

        Mockito.when(searchRepository.findBySearchKeyword(requestDto.getQuery()))
                .thenReturn(Optional.of(searchEntity));
        Mockito.when(kakaoSearchAPI.request(requestDto))
                .thenReturn(response);

        //when
        OpenAPIResponse openAPIResponse = searchService.searchAndIncreaseCount(requestDto);

        //then
        assertThat(openAPIResponse.getDocuments().get(0).getTitle()).isEqualTo("자바스크립트");
        assertThat(searchEntity.getCount()).isEqualTo(16);

        //verify
        verify(searchRepository).findBySearchKeyword(requestDto.getQuery());
        verify(kakaoSearchAPI).request(requestDto);
    }

    @Test
    @DisplayName("검색 서비스 테스트 - 인기검색어")
    public void popularTest() {

        //given
        List<SearchEntity> searchEntities = new ArrayList<>();
        searchEntities.add(new SearchEntity("자바", 15));
        searchEntities.add(new SearchEntity("자바스크립트", 10));
        Mockito.when(searchRepository.findTop10ByOrderByCountDesc())
                .thenReturn(searchEntities);

        //when
        List<SearchEntity> result = searchRepository.findTop10ByOrderByCountDesc();

        //then
        assertThat(result.get(0).getSearchKeyword()).isEqualTo("자바");

        //verify
        verify(searchRepository).findTop10ByOrderByCountDesc();
    }

}
