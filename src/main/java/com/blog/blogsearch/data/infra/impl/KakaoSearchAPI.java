package com.blog.blogsearch.data.infra.impl;

import com.blog.blogsearch.common.exception.RestAPIException;
import com.blog.blogsearch.common.exception.code.CommonErrorCode;
import com.blog.blogsearch.common.utils.JSONParserUtil;
import com.blog.blogsearch.data.APISource;
import com.blog.blogsearch.data.dto.Documents;
import com.blog.blogsearch.data.dto.Meta;
import com.blog.blogsearch.data.dto.SearchDto;
import com.blog.blogsearch.data.infra.SearchAPI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class KakaoSearchAPI implements SearchAPI {
    @Value("${kakao.key}")
    private String key;

    @Value("${kakao.baseUri}")
    private String baseUri;

    public SearchDto.Response request(SearchDto.Request request) {
        try {
            String response = getStringResponseFromKakaoApi(request);
            JSONObject apiResponse = JSONParserUtil.parseToJSON(response, JSONObject.class);

            JSONArray documentsArr = JSONParserUtil.parseToJSON(apiResponse.get("documents").toString(), JSONArray.class);
            List<Documents> documentsList = getDocuments(documentsArr);

            JSONObject metaJsonObject = JSONParserUtil.parseToJSON(apiResponse.get("meta").toString(), JSONObject.class);
            Meta meta = getMeta(metaJsonObject);
            return new SearchDto.Response(meta, documentsList);

        } catch (Exception e) {
            throw new RestAPIException(CommonErrorCode.SEARCH_API_EXCEPTION);
        }
    }

    private Meta getMeta(JSONObject metaJsonObject) {
        Long totalCount = (Long) metaJsonObject.get("total_count");
        Long pageableCount = (Long) metaJsonObject.get("pageable_count");
        Boolean isEnd = (Boolean) metaJsonObject.get("is_end");
        return new Meta(totalCount, pageableCount, isEnd, APISource.KAKAO);
    }

    private static List<Documents> getDocuments(JSONArray documentsArr) {
        List<Documents> documentsList = new ArrayList<>();
        for (Object o : documentsArr) {
            JSONObject aObj = JSONParserUtil.parseToJSON(o.toString(), JSONObject.class);
            String contents = aObj.get("contents").toString();
            String dateTime = aObj.get("datetime").toString();
            String title = aObj.get("title").toString();
            String url = aObj.get("url").toString();
            documentsList.add(new Documents(contents, dateTime, title, url));
        }
        return documentsList;
    }

    private String getStringResponseFromKakaoApi(SearchDto.Request request) throws IOException {
        URL url = makeUrl(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        String auth = "KakaoAK " + key;
        conn.setRequestProperty("Authorization", auth);

        int statusCode = conn.getResponseCode();
        BufferedReader rd;

        if (statusCode == HttpURLConnection.HTTP_OK) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();
    }

    private URL makeUrl(SearchDto.Request request) throws MalformedURLException {
        String query = "query=" + URLEncoder.encode(request.getQuery(), StandardCharsets.UTF_8) + "&";
        String sort = "sort=" + URLEncoder.encode(request.getSort(), StandardCharsets.UTF_8) + "&";
        String page = "page=" + request.getPage() + "&";
        String size = "size=" + request.getSize();
        return new URL(baseUri + "?" + query + sort + page + size);
    }

}
