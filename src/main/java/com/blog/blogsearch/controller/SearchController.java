package com.blog.blogsearch.controller;


import com.blog.blogsearch.data.dto.PopularDto;
import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/search")
    public ResponseEntity<SearchDto.Response> getBlogSearch(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "sort", defaultValue = "accuracy") String sort,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        SearchDto.Request request = new SearchDto.Request(query, sort, page, size);
        return ResponseEntity.ok(blogService.searchAndUpdateCount(request));
    }

    @GetMapping("/search/popular")
    public ResponseEntity<PopularDto.Response> getPopularTopTen() {
        PopularDto.Response response = blogService.getPopularTopTen();
        return ResponseEntity.ok(response);
    }
}
