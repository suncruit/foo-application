package com.blog.blogsearch.controller;

import com.blog.blogsearch.data.dto.*;
import com.blog.blogsearch.service.SearchCountService;
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

    @MockBean
    SearchCountService searchCountService;

    @Test
    @DisplayName("검색 컨트롤러 테스트")
    void searchTest() throws Exception {
        //given
        SearchRequestDto requestDto = new SearchRequestDto("자바스크립트", "accuracy", 1, 1);
        Meta meta = new Meta(1, 1, false);
        Document document = new Document("자바스크립트", "contents", "https://foo.com", "blog", "thum", "2023-03-11T00:00:00.000+09:00");
        List<Document> documentList = new ArrayList<>(List.of(document));
        KakaoAPIResponse kakaoAPIResponse = new KakaoAPIResponse(meta, documentList);
        OpenAPIResponse response = new OpenAPIResponse(kakaoAPIResponse, 1, 1);

        Mockito.when(searchService.searchAndIncreaseCount(requestDto))
                .thenReturn(response);

        mockMvc.perform(get("/search")
                        .param("query", "자바스크립트")
                        .param("sort", "accuracy")
                        .param("page", "1")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata.totalCount").value(1))
                .andExpect(jsonPath("$.documents[0].title").value("자바스크립트"))
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
        Mockito.when(searchCountService.getPopularTopTen())
                .thenReturn(new PopularResponseDto(popularKeywords));


        mockMvc.perform(get("/search/populars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.popularKeywordList[0].keyword").value("자바"))
                .andExpect(jsonPath("$.popularKeywordList[0].count").value("10"))
                .andDo(print());

    }
}
