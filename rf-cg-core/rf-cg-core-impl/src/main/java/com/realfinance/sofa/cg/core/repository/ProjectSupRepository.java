package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.proj.ProjectSup;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface ProjectSupRepository extends JpaRepositoryImplementation<ProjectSup, Integer> {


    List<ProjectSup> findByProject(Project project);
}
