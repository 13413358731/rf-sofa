package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.domain.SupplierAssessmentIndicator;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface SupplierAssessmentIndicatorRepository extends JpaRepositoryImplementation<SupplierAssessmentIndicator, Integer> {

    boolean existsById(String id);

}
