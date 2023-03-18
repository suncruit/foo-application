package com.blog.blogsearch.blog;

import com.blog.blogsearch.blog.dto.Documents;
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

    public List<Documents> search() {
        return searchAPI.search("이효리");
    }
}
