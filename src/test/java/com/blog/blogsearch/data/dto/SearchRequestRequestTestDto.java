package com.blog.blogsearch.data.dto;

import com.blog.blogsearch.common.exception.RestAPIException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SearchRequestRequestTestDto {

    @Test
    @DisplayName("입력값 정상")
    void validationSucceed() {
        SearchRequestDto request = new SearchRequestDto("자바", "accuracy", 1, 1);
        assertThat(request.getQuery()).isEqualTo("자바");
    }

    @Test
    @DisplayName("입력값 실패-sort 오류")
    void validationFailedBySort() {
        assertThatThrownBy(
                () -> new SearchRequestDto("자바", "acacuracy", 1, 1)
        ).isInstanceOf(RestAPIException.class);
    }

    @Test
    @DisplayName("입력값 실패-page 오류")
    void validationFailedByPage() {
        assertThatThrownBy(
                () -> new SearchRequestDto("자바", "accuracy", 0, 19)
        ).isInstanceOf(RestAPIException.class);
    }

    @Test
    @DisplayName("입력값 실패-size 오류")
    void validationFailedBySize() {
        assertThatThrownBy(
                () -> new SearchRequestDto("자바", "accuracy", 2, 51)
        ).isInstanceOf(RestAPIException.class);
    }
}
