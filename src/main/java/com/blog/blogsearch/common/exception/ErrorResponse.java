package com.blog.blogsearch.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String exceptionCode;
    private final String code;
    private final String message;

}
