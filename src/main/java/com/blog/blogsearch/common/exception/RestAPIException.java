package com.blog.blogsearch.common.exception;

import com.blog.blogsearch.common.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestAPIException extends RuntimeException {
    private final ErrorCode errorCode;
}
