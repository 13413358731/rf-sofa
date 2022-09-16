package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.proj.ProjectRelationship;
import com.realfinance.sofa.cg.core.domain.purresult.PurResultRelationship;
import com.realfinance.sofa.cg.core.domain.purresult.PurchaseResult;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface PurchaseResultRelationshipRepository extends JpaRepositoryImplementation<PurResultRelationship, Integer> {

    List<PurResultRelationship> findByPurchaseResult(PurchaseResult p);

    void deleteByPurchaseResult(PurchaseResult p);
}
