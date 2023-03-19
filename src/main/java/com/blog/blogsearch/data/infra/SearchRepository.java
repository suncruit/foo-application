package com.blog.blogsearch.data.infra;

import com.blog.blogsearch.data.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, Long> {


    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<SearchEntity> findBySearchKeyword(String searchKeyword);

    List<SearchEntity> findTop10ByOrderByCountDesc();
}
