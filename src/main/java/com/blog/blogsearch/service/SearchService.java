package com.blog.blogsearch.service;

import com.blog.blogsearch.common.exception.RestAPIException;
import com.blog.blogsearch.common.exception.code.CommonErrorCode;
import com.blog.blogsearch.data.dto.OpenAPIResponse;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final List<SearchAPI> searchAPIList;
    private final SearchCountService searchCountService;

    @Transactional
    public OpenAPIResponse searchAndIncreaseCount(SearchRequestDto searchRequestDto) throws Exception {
        OpenAPIResponse openAPIResponse = null;

        for (int i = 0; i < searchAPIList.size(); i++) {
            try {
                openAPIResponse = searchAPIList.get(i).request(searchRequestDto);
                break;
            } catch (Exception e) {
                //logging
                if (isLastAPI(i)) {
                    throw new RestAPIException(CommonErrorCode.SEARCH_API_EXCEPTION);
                }
            }
        }
        searchCountService.increaseCount(searchRequestDto);
        return openAPIResponse;
    }

    private boolean isLastAPI(int i) {
        return i == searchAPIList.size() - 1;
    }

}
