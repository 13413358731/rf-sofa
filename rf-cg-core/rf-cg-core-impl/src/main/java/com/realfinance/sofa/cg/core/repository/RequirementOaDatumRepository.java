package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.req.RequirementOaDatum;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface RequirementOaDatumRepository extends JpaRepositoryImplementation<RequirementOaDatum, Integer> {

    List<RequirementOaDatum> findByApprovalNoIn(List<String> approvalNoList);
}
