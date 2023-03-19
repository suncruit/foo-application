package com.blog.blogsearch.service;


import com.blog.blogsearch.data.APISource;
import com.blog.blogsearch.data.dto.Documents;
import com.blog.blogsearch.data.dto.Meta;
import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
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
@ContextConfiguration(classes = {SearchRepository.class, SearchService.class, SearchAPIService.class})
public class SearchServiceTest {

    @MockBean
    SearchAPIService searchAPIService;
    @MockBean
    SearchRepository searchRepository;
    @Autowired
    SearchService searchService;

    @Test
    @DisplayName("검색 서비스 테스트 - findBySearchKeyword()")
    public void findBySearchKeywordTest() {
        //given
        SearchDto.Request requestDto = new SearchDto.Request("자바스크립트", "accuracy", 1, 1);
        SearchEntity searchEntity = new SearchEntity(requestDto.getQuery(), 15);

        Meta meta = new Meta(10L, 10L, false, APISource.KAKAO);
        List<Documents> documentsList = new ArrayList<>(List.of(new Documents("자바스크립트", "2023-03-11T00:00:00.000+09:00", "test", "https://foo.com")));
        SearchDto.Response response = new SearchDto.Response(meta, documentsList);

        Mockito.when(searchRepository.findBySearchKeyword(requestDto.getQuery()))
                .thenReturn(Optional.of(searchEntity));
        Mockito.when(searchAPIService.request(requestDto))
                .thenReturn(response);

        //when
        SearchDto.Response searchResponse = searchService.searchAndIncreaseCount(requestDto);

        //then
        assertThat(searchResponse.getDocumentsList().get(0).getContents()).isEqualTo("자바스크립트");
        assertThat(searchEntity.getCount()).isEqualTo(16);

        //verify
        verify(searchRepository).findBySearchKeyword(requestDto.getQuery());
        verify(searchAPIService).request(requestDto);
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
