package com.blog.blogsearch.controller;


import com.blog.blogsearch.common.exception.RestAPIException;
import com.blog.blogsearch.common.exception.code.CommonErrorCode;
import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.PopularResponseDto;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import com.blog.blogsearch.service.SearchCountService;
import com.blog.blogsearch.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final SearchCountService searchCountService;

    @GetMapping()
    public ResponseEntity<OpenAPIResponse> getBlogSearch(
            SearchRequestDto request
    ) {
//        SearchRequestDto request = new SearchRequestDto(query, sort, page, size);
        try {
            return ResponseEntity.ok(searchService.searchAndIncreaseCount(request));
        } catch (Exception e) {
            throw new RestAPIException(CommonErrorCode.SEARCH_API_EXCEPTION);
        }
    }

    @GetMapping("/populars")
    public ResponseEntity<PopularResponseDto> getPopularTopTen() {
        PopularResponseDto response = searchCountService.getPopularTopTen();
        return ResponseEntity.ok(response);
    }
}
