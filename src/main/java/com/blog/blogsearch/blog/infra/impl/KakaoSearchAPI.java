package com.blog.blogsearch.blog.infra.impl;

import com.blog.blogsearch.blog.dto.Documents;
import com.blog.blogsearch.blog.infra.SearchAPI;
import com.blog.blogsearch.utils.JSONParserUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

    public List<Documents> search(String keyWord) {
        try {
            String response = getStringResponseFromKakaoApi(keyWord);
            JSONObject apiResponse = JSONParserUtil.parseToJSON(response, JSONObject.class);
            JSONArray documentsArr = JSONParserUtil.parseToJSON(apiResponse.get("documents").toString(), JSONArray.class);
            return getDocuments(documentsArr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("e");
        }
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

    private String getStringResponseFromKakaoApi(String keyWord) throws IOException {

        URL url = new URL(baseUri + URLEncoder.encode(keyWord, StandardCharsets.UTF_8));
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

}
