package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationMain;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface SupplierEvaluationRepository extends JpaRepositoryImplementation<SupplierEvaluationMain, Integer> {

    boolean existsById(String id);

}
