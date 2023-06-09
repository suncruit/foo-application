package com.blog.blogsearch.data.entity;

import com.blog.blogsearch.data.dto.PopularKeyword;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchEntityTest {

    private static SearchEntity searchEntity;

    @BeforeEach
    void setUp() {
        searchEntity = new SearchEntity("자바", 0);
    }

    @Test
    @DisplayName("dto -> entity 테스트")
    void fromDtoTest() {
        //given
        SearchRequestDto dto = new SearchRequestDto("자바", "accuracy", 1, 1);
        //when
        SearchEntity searchEntity = SearchEntity.fromDto(dto);
        //then
        Assertions.assertThat(searchEntity).isEqualTo(searchEntity);
    }

    @Test
    @DisplayName("entity -> 인기검색어 테스트")
    void toPopularKeyword() {
        //when
        PopularKeyword popularKeyword = searchEntity.toPopularKeyword();
        //then
        Assertions.assertThat(popularKeyword).isEqualTo(new PopularKeyword("자바", 0));
    }

    @Test
    @DisplayName("카운트 증가 테스트")
    public void countAddTest() {
        SearchEntity searchEntity1 = new SearchEntity("자바", 0);
        searchEntity1.increaseCount();
        Assertions.assertThat(searchEntity1.getCount()).isEqualTo(1);
    }
}
