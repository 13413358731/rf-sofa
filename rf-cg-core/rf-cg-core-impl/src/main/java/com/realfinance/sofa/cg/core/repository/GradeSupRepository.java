package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.meeting.GradeSup;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface GradeSupRepository extends JpaRepositoryImplementation<GradeSup, Integer> {
    @Modifying
    @Query("update GradeSup m set m.score = ?1 where m.id = ?2")
    int setScoreById(Integer score, Integer id);
}
