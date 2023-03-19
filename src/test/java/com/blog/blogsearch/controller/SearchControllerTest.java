package com.blog.blogsearch.controller;

import com.blog.blogsearch.data.dto.*;
import com.blog.blogsearch.service.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    SearchService searchService;

    @Test
    @DisplayName("검색 컨트롤러 테스트")
    void searchTest() throws Exception {
        //given
        SearchDto.Request requestDto = new SearchDto.Request("자바스크립트", "accuracy", 1, 1);
        Meta meta = new Meta(10L, 10L, false);
        List<Documents> documentsList = new ArrayList<>(List.of(new Documents("자바스크립트", "2023-03-11T00:00:00.000+09:00", "test", "https://foo.com")));
        SearchDto.Response response = new SearchDto.Response(meta, documentsList);

        Mockito.when(searchService.searchAndIncreaseCount(requestDto))
                .thenReturn(response);

        mockMvc.perform(get("/search")
                        .param("query", "자바스크립트")
                        .param("sort", "accuracy")
                        .param("page", "1")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.totalCount").value(10L))
                .andExpect(jsonPath("$.documentsList[0].contents").value("자바스크립트"))
                .andDo(print());

        //verify
        verify(searchService).searchAndIncreaseCount(requestDto);
    }

    @Test
    @DisplayName("인기 검색어 컨트롤러 테스트")
    void popularTest() throws Exception {

        //given
        PopularKeyword java = new PopularKeyword("자바", 10);
        PopularKeyword javascript = new PopularKeyword("자바스크립트", 5);
        PopularKeyword c = new PopularKeyword("C", 3);
        ArrayList<PopularKeyword> popularKeywords = new ArrayList<>(List.of(java, javascript, c));
        Mockito.when(searchService.getPopularTopTen())
                .thenReturn(new PopularDto.Response(popularKeywords));


        mockMvc.perform(get("/search/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.popularKeywordList[0].keyword").value("자바"))
                .andExpect(jsonPath("$.popularKeywordList[0].count").value("10"))
                .andDo(print());

    }
}
