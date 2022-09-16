package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.meeting.Meeting;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.time.LocalDateTime;

public interface MeetingRepository extends JpaRepositoryImplementation<Meeting, Integer> {
    @Modifying
    @Query("update Meeting m set m.resolutionContent = ?1 where m.id = ?2")
    int setContentById(String resolutionContent, Integer id);

    @Modifying
    @Query("update Meeting m set m.isGraded = ?1 where m.id = ?2")
    int setIsGradedById(Boolean isGraded, Integer id);

    @Modifying
    @Query("update Meeting m set m.finishGrade = ?1 where m.id = ?2")
    int setFinishGradeById(Boolean finishGrade, Integer id);

    @Modifying
    @Query("update Meeting m set m.endTime = ?1 where m.id = ?2")
    int setEndTimeById(LocalDateTime endTime, Integer id);

    @Modifying
    @Query("update Meeting m set m.filePrice = ?1 where m.id = ?2")
    int setFilePriceById(Boolean filePrice, Integer id);

    @Modifying
    @Query("update Meeting m set m.fileTecBusiness = ?1 where m.id = ?2")
    int setFileTecBusinessById(Boolean fileTecBusiness, Integer id);

}
