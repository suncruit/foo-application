package com.blog.blogsearch.data.dto;

import com.blog.blogsearch.common.exception.RestAPIException;
import com.blog.blogsearch.common.exception.code.ValidationErrorCode;
import com.blog.blogsearch.data.SortCondition;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@Getter
@EqualsAndHashCode
public class SearchRequestDto {
    private final String query;
    private final String sort;
    private final Integer page;
    private final Integer size;


    private static final Integer PAGE_MAX = 50;
    private static final Integer PAGE_MIN = 1;
    private static final Integer SIZE_MAX = 50;
    private static final Integer SIZE_MIN = 1;
    private static final Integer DEFAULT_SIZE = 10;
    private static final Integer DEFAULT_PAGE = 1;
    private static final String DEFAULT_SORT = "accuracy";

    public SearchRequestDto(Optional<String> query, Optional<String> sort, Optional<Integer> page, Optional<Integer> size) {
        this.query = trim(query);
        this.sort = validateSort(sort);
        this.page = validatePage(page);
        this.size = validateSize(size);
    }

    private String trim(Optional<String> query) {
        if (query.isEmpty()) throw new RestAPIException(ValidationErrorCode.SEARCH_KEYWORD_NOT_FOUND);
        return query.get().trim();
    }

    private Integer validatePage(Optional<Integer> page) {
        if (page.isEmpty()) return DEFAULT_PAGE;
        if (page.get() > PAGE_MAX || page.get() < PAGE_MIN)
            throw new RestAPIException(ValidationErrorCode.INVALID_PAGE);
        return page.get();
    }

    private Integer validateSize(Optional<Integer> size) {
        if (size.isEmpty()) return DEFAULT_SIZE;
        if (size.get() > SIZE_MAX || size.get() < SIZE_MIN)
            throw new RestAPIException(ValidationErrorCode.INVALID_SIZE);
        return size.get();
    }

    private String validateSort(Optional<String> sort) {
        if (sort.isEmpty()) return DEFAULT_SORT;
        if (!SortCondition.isValidSortCondition(sort.get()))
            throw new RestAPIException(ValidationErrorCode.INVALID_SORT);
        return sort.get();
    }
}
