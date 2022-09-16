package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionAtt;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface MeetingFileRepository extends JpaRepositoryImplementation<ProjectExecutionAtt, Integer> {
}
