package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.proj.ProjectSup;
import com.realfinance.sofa.cg.core.domain.purresult.PurResultSupplier;
import com.realfinance.sofa.cg.core.domain.purresult.PurchaseResult;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface PurResultSupRepository extends JpaRepositoryImplementation<PurResultSupplier, Integer> {
    List<PurResultSupplier> findByPurchaseResult(PurchaseResult p);
}
