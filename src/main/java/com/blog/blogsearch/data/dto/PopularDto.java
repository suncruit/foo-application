package com.blog.blogsearch.data.dto;

import lombok.Getter;

import java.util.List;

public class PopularDto {


    @Getter
    public static class Response {
        List<PopularKeyword> popularKeywordList;

        public Response(List<PopularKeyword> popularKeywordList) {
            this.popularKeywordList = popularKeywordList;
        }
    }
}
