package com.blog.blogsearch.data.entity;

import com.blog.blogsearch.data.dto.PopularKeyword;
import com.blog.blogsearch.data.dto.SearchRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "SEARCH")
@Entity
public class SearchEntity {

    @Id
    @Column(name = "SEARCH_KEYWORD")
    private String searchKeyword;

    @ColumnDefault("0")
    private Integer count;

    public static SearchEntity fromDto(SearchRequestDto searchRequestDto) {
        return new SearchEntity(searchRequestDto.getQuery(), 0);
    }

    public PopularKeyword toPopularKeyword() {
        return new PopularKeyword(this.searchKeyword, this.count);
    }

    public synchronized void increaseCount() {
        count++;
    }
}


