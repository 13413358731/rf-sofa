package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ProjectExecutionRepository extends JpaRepositoryImplementation<ProjectExecution, Integer> {


    ProjectExecution findByProject(Project project);
}
