package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationSheetMain;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface SupplierEvaluationSheetRepository extends JpaRepositoryImplementation<SupplierEvaluationSheetMain, Integer> {

    boolean existsById(String id);

}
