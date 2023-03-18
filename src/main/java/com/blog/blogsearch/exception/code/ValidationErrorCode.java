package com.blog.blogsearch.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ValidationErrorCode implements ErrorCode {
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "V001", "페이지 조회는 1이상 50이하 범위입니다."),
    INVALID_SIZE(HttpStatus.BAD_REQUEST, "V002", "페이지당 사이즈는 1이상 50이하 범위입니다."),

    INVALID_SORT(HttpStatus.BAD_REQUEST, "V003", "정렬 기준은 'accuracy'(정확도) 또는 'recency'(최신순) 입니다.");

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;
}
