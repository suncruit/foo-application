package com.blog.blogsearch.blog.infra;

import com.blog.blogsearch.blog.dto.Documents;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchAPI {

   List<Documents> search(String keyWord);
}
