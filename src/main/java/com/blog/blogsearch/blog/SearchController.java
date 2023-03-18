package com.blog.blogsearch.blog;


import com.blog.blogsearch.blog.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @RequestMapping("/search")
    public ResponseEntity<SearchDto.Response> search(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "sort", defaultValue = "accuracy") String sort,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        SearchDto.Request request = new SearchDto.Request(query, sort, page, size);
        return ResponseEntity.ok(searchService.search(request));
    }
}
