package com.blog.blogsearch.common.utils;

import com.blog.blogsearch.common.exception.RestAPIException;
import org.assertj.core.api.Assertions;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JSONParserUtilTest {

    @Test
    @DisplayName("JSON 파싱 성공")
    void parseSucceed() {
        //given
        String target = "        {\n" +
                "            \"keyword\": \"java\",\n" +
                "            \"count\": 1\n" +
                "        }";
        //when
        JSONObject jsonObject = JSONParserUtil.parseToJSON(target, JSONObject.class);

        //then
        Assertions.assertThat(jsonObject.get("keyword")).isEqualTo("java");
    }


    @Test
    @DisplayName("JSON 파싱 실패")
    void parseFailed() {
        //given
        String target = "        {\n" +
                "            \"keyword\": \"java\",\n" +
                "            \"count\":z, 1\n" +
                "        }";

        //then
        Assertions.assertThatThrownBy(
                () -> JSONParserUtil.parseToJSON(target, JSONObject.class)
        ).isInstanceOf(RestAPIException.class);
    }
}
