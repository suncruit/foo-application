package com.blog.blogsearch.controller;


import com.blog.blogsearch.data.dto.PopularDto;
import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping()
    public ResponseEntity<SearchDto.Response> getBlogSearch(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "sort", defaultValue = "accuracy") String sort,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        SearchDto.Request request = new SearchDto.Request(query, sort, page, size);
        return ResponseEntity.ok(searchService.searchAndIncreaseCount(request));
    }

    @GetMapping("/popular")
    public ResponseEntity<PopularDto.Response> getPopularTopTen() {
        PopularDto.Response response = searchService.getPopularTopTen();
        return ResponseEntity.ok(response);
    }
}
