package com.blog.blogsearch.exception;

import com.blog.blogsearch.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestAPIException extends RuntimeException {
    private final ErrorCode errorCode;
}
