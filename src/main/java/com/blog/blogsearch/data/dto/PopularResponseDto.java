package com.blog.blogsearch.data.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PopularResponseDto {
    List<PopularKeyword> popularKeywordList;

    public PopularResponseDto(List<PopularKeyword> popularKeywordList) {
        this.popularKeywordList = popularKeywordList;
    }
}
