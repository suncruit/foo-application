package com.blog.blogsearch.data.dto;

import com.blog.blogsearch.common.exception.RestAPIException;
import com.blog.blogsearch.common.exception.code.ValidationErrorCode;
import com.blog.blogsearch.data.SortCondition;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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

    public SearchRequestDto(String query, String sort, Integer page, Integer size) {
        this.query = trim(query);
        this.sort = validateSort(sort);
        this.page = validatePage(page);
        this.size = validateSize(size);
    }

    private String trim(String query) {
        return query.trim();
    }

    private Integer validatePage(Integer page) {
        if (page > PAGE_MAX || page < PAGE_MIN) throw new RestAPIException(ValidationErrorCode.INVALID_PAGE);
        return page;
    }

    private Integer validateSize(Integer size) {
        if (size > SIZE_MAX || size < SIZE_MIN) throw new RestAPIException(ValidationErrorCode.INVALID_SIZE);
        return size;
    }

    private String validateSort(String sort) {
        if (!SortCondition.isValidSortCondition(sort)) throw new RestAPIException(ValidationErrorCode.INVALID_SORT);
        return sort;
    }
}
