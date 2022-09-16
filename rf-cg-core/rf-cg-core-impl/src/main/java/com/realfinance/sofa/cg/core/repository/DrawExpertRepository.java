package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.DrawExpert;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DrawExpertRepository extends JpaRepositoryImplementation<DrawExpert, Integer> {

    boolean existsById(String id);

    boolean existsByProjectId(Integer id);

    Optional<DrawExpert> findByProjectId(Integer projectId);

    @Modifying
    @Query("update DrawExpert m set m.endTime = ?1 where m.id = ?2")
    int setEndTimeById(LocalDateTime endTime, Integer id);

}
