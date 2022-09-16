package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.Supplier;
import com.realfinance.sofa.cg.sup.domain.SupplierBlacklist;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface SupplierBlacklistRepository extends JpaRepositoryImplementation<SupplierBlacklist, Integer> {

    boolean existsBySupplierAndValid(Supplier supplier, Boolean valid);
}
