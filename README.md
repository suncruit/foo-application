# 블로그 검색 서비스

### API 명세

---

**1.블로그 검색 API**

GET /search

Request Parameter

| Name  |  Type   | Description | Required |
|:-----:|:-------:|:-----------:|:--------:|
| query | String  |     검색어     |    O     |
| sort  | String  |    정렬기준     |    X     |
| page  | Integer |    조회페이지    |    X     |
| size  | Integer |    조회사이즈    |    X     |

요청 예시

```shell
curl -v -G --data-urlencode "query=한글" localhost:8080/search
```

Response

metadata

|    Name    |  Type   | Description |
|:----------:|:-------:|:-----------:|
| totalCount | Integer |  검색된 문서 수   |
|    page    | Integer |  결과 페이지 번호  |
|    size    | Integer |  한 페이지 사이즈  |

documents

|   Name    |   Type   | Description  |
|:---------:|:--------:|:------------:|
|   title   |  String  |   블로그 글 제목   |
| contents  |  String  |  	블로그 글 요약   |
|    url    |  String  |  블로그 글 URL   |
| blogname  |  String  |   블로그의 이름    |
| thumbnail |  String  | 미리보기 이미지 URL |
| dateTime  | Datetime |  블로그 글 작성시간  |

---

**2.인기검색어 탑10 API**

GET /search/popular

요청 예시

```
curl -v -G localhost:8080/search/populars 
```

Response

popularKeywordList

|  Name   |  Type   | Description |
|:-------:|:-------:|:-----------:|
| keyword | String  |   검색 키워드    |
|  count  | Integer |    검색된 수    |
