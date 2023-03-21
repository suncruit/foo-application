package com.blog.blogsearch.controller;


import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.PopularResponseDto;
import com.blog.blogsearch.data.dto.SearchRequestDto;
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
    public ResponseEntity<OpenAPIResponse> getBlogSearch(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "sort", defaultValue = "accuracy") String sort,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        SearchRequestDto request = new SearchRequestDto(query, sort, page, size);
        return ResponseEntity.ok(searchService.searchAndIncreaseCount(request));
    }

    @GetMapping("/populars")
    public ResponseEntity<PopularResponseDto> getPopularTopTen() {
        PopularResponseDto response = searchService.getPopularTopTen();
        return ResponseEntity.ok(response);
    }
}
