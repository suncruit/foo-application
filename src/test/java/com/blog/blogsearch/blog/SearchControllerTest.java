package com.blog.blogsearch.blog;

import com.blog.blogsearch.blog.controller.SearchController;
import com.blog.blogsearch.blog.dto.Documents;
import com.blog.blogsearch.blog.dto.Meta;
import com.blog.blogsearch.blog.dto.SearchDto;
import com.blog.blogsearch.blog.service.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
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
    @DisplayName("search 테스트")
    void searchTest() throws Exception {
        //given
        SearchDto.Request request = new SearchDto.Request("테스트", "accuracy", 1, 1);

        Meta meta = new Meta(10L, 10L, false);
        List<Documents> documentsList = new ArrayList<>(List.of(new Documents("foo-contents", "2023-03-11T00:00:00.000+09:00", "test", "https://foo.com")));
        SearchDto.Response response = new SearchDto.Response(meta, documentsList);

        given(searchService.search(request)).willReturn(response);


        //when
        mockMvc.perform(get("/search")
                        .param("query", "테스트")
                        .param("sort", "accuracy")
                        .param("page", "1")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.totalCount").value(10L))
                .andExpect(jsonPath("$.documentsList[0].contents").value("foo-contents"))
                .andDo(print());

        //verify
        verify(searchService).search(request);
    }
}
