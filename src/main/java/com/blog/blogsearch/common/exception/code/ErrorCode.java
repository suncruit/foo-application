package com.blog.blogsearch.common.exception.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();

    HttpStatus getHttpStatus();

    String getMessage();

    String getCustomCode();
}

