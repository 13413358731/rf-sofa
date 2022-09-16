package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.purresult.PurchaseResult;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface PurchaseResultRepository extends JpaRepositoryImplementation<PurchaseResult, Integer> {

    boolean existsByTenantIdAndProjectexeId(String tenantId, Integer code);

}
