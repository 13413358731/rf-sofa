package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.domain.PurchaseResultNotice;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;
import java.util.Optional;

public interface PurchaseResultNoticeRepository extends JpaRepositoryImplementation<PurchaseResultNotice,Integer> {
    Optional<PurchaseResultNotice> findByIdAndTenantId(String businessKey, String tenantId);

    List<PurchaseResultNotice> findByProjectIdIn(List<Integer> projectIds);

    List<PurchaseResultNotice> findByPassTimeNotNull();

    Optional<PurchaseResultNotice> findByProjectId(Integer projectId);

}
