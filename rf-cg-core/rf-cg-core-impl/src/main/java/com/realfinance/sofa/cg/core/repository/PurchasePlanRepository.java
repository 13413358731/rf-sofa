package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.plan.PurchasePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface PurchasePlanRepository extends JpaRepositoryImplementation<PurchasePlan,Integer> {

    Page<PurchasePlan>  findAllByAnnualPlan(Integer id, Pageable pageable);


}
