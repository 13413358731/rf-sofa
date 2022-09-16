package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface SupplierAssessmentRepository extends JpaRepositoryImplementation<SupplierAssessment, Integer> {

    boolean existsById(String id);

}
