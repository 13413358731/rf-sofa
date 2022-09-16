package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.meeting.AuditQualification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface AuditQualRepository extends JpaRepositoryImplementation<AuditQualification, Integer> {
    boolean existsById(String id);

}
