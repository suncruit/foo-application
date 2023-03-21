package com.blog.blogsearch.service;

import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
import org.assertj.core.api.Assertions;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SearchCountService.class, SearchRepository.class})
public class SearchCountTest {

    @Autowired
    SearchCountService searchCountService;
    @MockBean
    SearchRepository searchRepository;

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

    //테스트 실패
    @Test
    @DisplayName("카운트 서비스 테스트 - 비관적 락 테스트")
    void savePopularWordMultiThread() throws InterruptedException {
        String query = "자바";
        SearchRequestDto searchRequestDto = new SearchRequestDto(query, "accuracy", 1, 19);
        SearchEntity searchEntity = new SearchEntity(query, 0);
        Mockito.when(searchRepository.findBySearchKeyword(searchRequestDto.getQuery())).thenReturn(Optional.of(searchEntity));

        // given
        int threadCount = 1000;
        AtomicInteger successCount = new AtomicInteger();
        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            service.execute(() -> {
                searchCountService.increaseCount(searchRequestDto);
                successCount.getAndIncrement();
                latch.countDown();
            });
        }
        latch.await();

        SearchEntity searchEntity1 = searchRepository.findBySearchKeyword(query).get();

        // then
        Assertions.assertThat(searchEntity1.getCount()).isEqualTo(successCount.get());
    }
}
