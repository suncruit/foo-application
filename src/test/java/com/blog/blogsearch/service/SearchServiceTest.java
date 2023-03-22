package com.blog.blogsearch.service;


import com.blog.blogsearch.data.infra.impl.KakaoSearchAPI;
import com.blog.blogsearch.data.infra.impl.NaverSearchAPI;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
//@Import
@ContextConfiguration(classes = {SearchService.class, KakaoSearchAPI.class, NaverSearchAPI.class, SearchCountService.class})
public class SearchServiceTest {
    @MockBean
    KakaoSearchAPI kakaoSearchAPI;

    @MockBean
    SearchCountService searchCountService;
    @Autowired
    SearchService searchService;
}
