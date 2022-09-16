package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.domain.req.RequirementSup;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface RequirementSupRepository extends JpaRepositoryImplementation<RequirementSup, Integer> {


    List<RequirementSup> findByRequirement(Requirement requirement);
}
