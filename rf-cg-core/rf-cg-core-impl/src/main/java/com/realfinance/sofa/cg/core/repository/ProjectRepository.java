package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;

public interface ProjectRepository extends JpaRepositoryImplementation<Project, Integer> {

    Optional<Project> findByRequirement(Requirement requirement);
}
