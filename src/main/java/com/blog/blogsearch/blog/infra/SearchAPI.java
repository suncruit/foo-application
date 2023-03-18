package com.blog.blogsearch.blog.infra;

import com.blog.blogsearch.blog.dto.SearchDto;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchAPI {

    SearchDto.Response search(SearchDto.Request request);
}
