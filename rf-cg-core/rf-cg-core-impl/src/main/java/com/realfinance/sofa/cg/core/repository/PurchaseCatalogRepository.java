package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.PurchaseCatalog;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface PurchaseCatalogRepository extends JpaRepositoryImplementation<PurchaseCatalog, Integer> {

    boolean existsByTenantIdAndCode(String tenantId, String code);

    int countByParent(PurchaseCatalog parent);

    List<PurchaseCatalog>  findByNameIn(List<String> nameList);
}
