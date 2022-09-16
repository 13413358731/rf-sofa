package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.SupplierLabel;
import com.realfinance.sofa.cg.sup.domain.SupplierLabelType;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface SupplierLabelRepository extends JpaRepositoryImplementation<SupplierLabel, Integer> {

    List<SupplierLabel> findByTenantIdAndParentIdIsNull(String tenantId);

    int countByParent(SupplierLabel parent);

    boolean existsByTenantIdAndTypeAndValue(String tenantId, SupplierLabelType type, String value);
}
