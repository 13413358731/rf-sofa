package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.meeting.MeetingConferee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.time.LocalDateTime;

public interface MeetingConfereeRepository extends JpaRepositoryImplementation<MeetingConferee, Integer> {
    @Modifying
    @Query("update MeetingConferee m set m.resolutionContent = ?1 where m.id = ?2")
    int setContentById(Integer resolutionContent, Integer id);

    @Modifying
    @Query("update MeetingConferee m set m.signInTime = ?1 where m.id = ?2")
    int setSignInTimeById(LocalDateTime signInTime, Integer id);
}
