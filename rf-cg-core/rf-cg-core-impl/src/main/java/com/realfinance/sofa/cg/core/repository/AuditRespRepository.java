package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.meeting.AuditResponse;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface AuditRespRepository extends JpaRepositoryImplementation<AuditResponse, Integer> {
    boolean existsById(String id);

}
