package com.blog.blogsearch.data.entity;

import com.blog.blogsearch.data.dto.PopularKeyword;
import com.blog.blogsearch.data.dto.SearchDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "SEARCH")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchEntity {

    @Id
    @Column(name = "SEARCH_KEYWORD")
    private String searchKeyword;

    @ColumnDefault("0")
    private Integer count;

    public static SearchEntity fromDto(SearchDto.Request searchDto) {
        return new SearchEntity(searchDto.getQuery(), 0);
    }

    public PopularKeyword toPopularKeyword() {
        return new PopularKeyword(this.searchKeyword, this.count);
    }

    public void increaseCount() {
        count++;
    }
}


