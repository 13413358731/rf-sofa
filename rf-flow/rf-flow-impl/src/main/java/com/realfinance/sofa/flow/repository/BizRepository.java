package com.realfinance.sofa.flow.repository;

import com.realfinance.sofa.flow.domain.Biz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;

public interface BizRepository extends JpaRepositoryImplementation<Biz, Integer> {

    Optional<Biz> findByCode(String code);

    @Query("SELECT b FROM Biz b WHERE (b.code LIKE :filter OR b.name LIKE :filter)")
    Page<Biz> findByFilter(String filter, Pageable pageable);
}
