package com.blog.blogsearch.service;

import com.blog.blogsearch.HelloInterface;
import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.data.infra.SearchAPI;
import com.blog.blogsearch.data.infra.SearchRepository;
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
