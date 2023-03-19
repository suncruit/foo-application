package com.blog.blogsearch.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    UNSUPPORTED_PARAMETER(HttpStatus.BAD_REQUEST, "C001", "지원하지 않는 형식"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C002", "잘못된 파라미터"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "Internal server error"),
    SEARCH_API_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "C004", "검색 API 관련 에러"),

    PARSE_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "C005", "파싱 관련 에러");
    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

}
