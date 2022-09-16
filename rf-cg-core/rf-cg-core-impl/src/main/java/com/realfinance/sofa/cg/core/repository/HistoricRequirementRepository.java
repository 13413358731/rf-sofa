package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.req.HistoricRequirement;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface HistoricRequirementRepository extends JpaRepositoryImplementation<HistoricRequirement, Integer> {

    Page<HistoricRequirement> findByRequirement(Requirement requirement, Pageable pageable);
    List<HistoricRequirement> findByRequirement(Requirement requirement, Sort sort);
}
