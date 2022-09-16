package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.proj.HistoricProject;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface HistoricProjectRepository extends JpaRepositoryImplementation<HistoricProject, Integer> {

    Page<HistoricProject> findByProject(Project project, Pageable pageable);
    List<HistoricProject> findByProject(Project project, Sort sort);
}
