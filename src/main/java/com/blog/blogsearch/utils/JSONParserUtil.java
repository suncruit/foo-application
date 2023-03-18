package com.blog.blogsearch.utils;

import com.blog.blogsearch.exception.RestAPIException;
import com.blog.blogsearch.exception.code.CommonErrorCode;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONParserUtil {
    private static final JSONParser parser = new JSONParser();

    public static <T> T parseToJSON(String target, Class<T> responseType) {
        try {
            Object obj = parser.parse(target);
            return responseType.cast(obj);
        } catch (ParseException e) {
            throw new RestAPIException(CommonErrorCode.SEARCH_API_EXCEPTION);
        }
    }
}
