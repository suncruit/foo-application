# 블로그 검색 서비스

todo

- 동시성 테스트(부하테스트?)
    - api 부분 일부만 Mocking 해서?
    - 아니면 service 하나 더 분리?
- Repository 테스트
- 리드미 최신화
- NaverAPI 테스트

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
curl -v -G "localhost:8080/search?query=j"
```

Response

meta

|     Name     |  Type   |       Description        |
|:------------:|:-------:|:------------------------:|
|  totalCount  | Integer |         검색된 문서 수         |
| pageableSize | Integer | total_count 중 노출 가능 문서 수 |
|    isEnd     | Boolean |       다음페이지 존재 여부        |
|  apiSource   | String  |          정보 출처           |

documents

|   Name   |   Type   | Description |
|:--------:|:--------:|:-----------:|
|  title   |  String  |    문서 제목    |
| contents |  String  | 문서 본문 중 일부  |
|   url    |  String  |   문서 URL    |
| dateTime | Datetime |  문서 글 작성시간  |

---

**2.인기검색어 탑10 API**

GET /search/popular

요청 예시

```
curl -X GET "http://localhost:8080/search/popular" 
```

Response

popularKeywordList

|  Name   |  Type   | Description |
|:-------:|:-------:|:-----------:|
| keyword | String  |   검색 키워드    |
|  count  | Integer |    검색된 수    |
