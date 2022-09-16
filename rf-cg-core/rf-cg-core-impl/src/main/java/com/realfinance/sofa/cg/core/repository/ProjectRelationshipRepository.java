package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.proj.ProjectRelationship;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.domain.req.RequirementRelationship;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface ProjectRelationshipRepository extends JpaRepositoryImplementation<ProjectRelationship, Integer> {

    List<ProjectRelationship> findByProject(Project p);

    void deleteByProject(Project r);
}
