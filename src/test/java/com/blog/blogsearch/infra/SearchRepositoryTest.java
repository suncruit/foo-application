package com.blog.blogsearch.infra;

import com.blog.blogsearch.data.entity.SearchEntity;
import com.blog.blogsearch.data.infra.SearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SearchRepositoryTest {

    @Autowired
    SearchRepository searchRepository;

    final String keyword = "자바";

    @Test
    @DisplayName("카운트 증가 테스트")
    @Transactional
    void single() {
        searchRepository.save(new SearchEntity(keyword, 0));
        SearchEntity searchEntity = searchRepository.findBySearchKeyword(keyword).get();
        searchEntity.increaseCount();
        searchEntity.increaseCount();
        List<SearchEntity> top10ByOrderByCountDesc = searchRepository.findTop10ByOrderByCountDesc();
        Integer count = top10ByOrderByCountDesc.get(0).getCount();
        assertThat(count).isEqualTo(2);
    }
}
