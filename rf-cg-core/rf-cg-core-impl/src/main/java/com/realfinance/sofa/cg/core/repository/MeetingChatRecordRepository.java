package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.meeting.MeetingChatRecord;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MeetingChatRecordRepository extends JpaRepositoryImplementation<MeetingChatRecord, Integer> {
}
