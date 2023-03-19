package com.blog.blogsearch.blog.service;

import com.blog.blogsearch.blog.HelloInterface;
import com.blog.blogsearch.blog.SearchRepository;
import com.blog.blogsearch.blog.dto.SearchDto;
import com.blog.blogsearch.blog.infra.SearchAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final SearchAPI searchAPI;

    public String hello() {
        List<HelloInterface> helloString = searchRepository.findHelloString();
        return helloString.get(0).getHello();
    }

    public SearchDto.Response search(SearchDto.Request searchDto) {
        return searchAPI.search(searchDto);
    }
}
