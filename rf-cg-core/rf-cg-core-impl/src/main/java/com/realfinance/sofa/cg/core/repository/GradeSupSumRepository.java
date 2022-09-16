package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.meeting.GradeSupSum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface GradeSupSumRepository extends JpaRepositoryImplementation<GradeSupSum, Integer> {
    boolean existsById(String id);

    @Modifying
    @Query("update GradeSupSum m set m.sumScore = ?1 where m.id = ?2")
    int setSumScoreById(Double sumScore, Integer id);

    @Modifying
    @Query("update GradeSupSum m set m.ranking = ?1 where m.id = ?2")
    int setSumRankingById(Integer ranking, Integer id);

    @Modifying
    @Query("update GradeSupSum m set m.IsSum = ?1 where m.id = ?2")
    int setIsSumById(Boolean IsSum, Integer id);

}
