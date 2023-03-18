package com.blog.blogsearch.blog;


import com.blog.blogsearch.blog.dto.Documents;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @RequestMapping("/search")
    public ResponseEntity<List<Documents>> search() {
        return ResponseEntity.ok(searchService.search());
    }
}
