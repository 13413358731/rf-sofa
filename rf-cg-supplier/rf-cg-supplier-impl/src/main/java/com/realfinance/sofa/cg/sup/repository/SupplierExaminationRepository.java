package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.FlowStatus;
import com.realfinance.sofa.cg.sup.domain.SupplierAccount;
import com.realfinance.sofa.cg.sup.domain.SupplierExamination;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;

public interface SupplierExaminationRepository extends JpaRepositoryImplementation<SupplierExamination, Integer> {

    boolean existsByAccountAndStatusEquals(SupplierAccount account, FlowStatus status);

    Optional<SupplierExamination> findByAccountAndCategoryAndStatus(SupplierAccount account, SupplierExamination.Category category, FlowStatus status);
}
