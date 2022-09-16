package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationMain;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationProcessMng;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface SupEvaluationProcessMngRepository extends JpaRepositoryImplementation<SupplierEvaluationProcessMng, Integer> {

    boolean existsById(String id);

}
