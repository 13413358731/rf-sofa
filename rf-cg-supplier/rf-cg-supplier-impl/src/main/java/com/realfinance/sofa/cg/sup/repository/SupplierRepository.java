package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.Supplier;
import com.realfinance.sofa.cg.sup.domain.SupplierLabel;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Set;

public interface SupplierRepository extends JpaRepositoryImplementation<Supplier, Integer> {

    boolean existsByTenantIdAndUnifiedSocialCreditCode(String tenantId, String unifiedSocialCreditCode);

    int countByIdInAndSupplierLabels(Set<Integer> ids, SupplierLabel supplierLabel);

}
