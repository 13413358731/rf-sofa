package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.meeting.MeetingChatRecord;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ChatRecordRepository extends JpaRepositoryImplementation<MeetingChatRecord, Integer> {

}
