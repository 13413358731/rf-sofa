package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.Supplier;
import com.realfinance.sofa.cg.sup.domain.SupplierCredit;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface SupplierCreditRepository extends JpaRepositoryImplementation<SupplierCredit, Integer> {



    List<SupplierCredit> findBySupplierIsIn(List<Supplier> list);

    void deleteByIdIn(List<Integer> ids);

}
