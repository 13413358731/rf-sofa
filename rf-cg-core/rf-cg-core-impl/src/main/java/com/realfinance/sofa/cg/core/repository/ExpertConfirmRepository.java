package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.domain.ExpertConfirm;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ExpertConfirmRepository extends JpaRepositoryImplementation<ExpertConfirm, Integer> {

    boolean existsById(String id);

//    boolean existsByExpertAndValid(Expert expert, Boolean valid);

}
