package com.blog.blogsearch.data.infra;

import com.blog.blogsearch.data.dto.SearchDto;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchAPI {

    SearchDto.Response search(SearchDto.Request request);
}
