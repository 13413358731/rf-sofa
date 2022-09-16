package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.domain.req.RequirementRelationship;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface RequirementRelationshipRepository extends JpaRepositoryImplementation<RequirementRelationship, Integer> {

    List<RequirementRelationship> findByRequirement(Requirement r);

    void deleteByRequirement(Requirement r);
}
