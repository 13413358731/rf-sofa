package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.ExpertLabel;
import com.realfinance.sofa.cg.core.domain.ExpertLabelType;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface ExpertLabelRepository extends JpaRepositoryImplementation<ExpertLabel, Integer> {

    List<ExpertLabel> findByTenantIdAndParentIdIsNull(String tenantId);

    int countByParent(ExpertLabel parent);

    boolean existsByTenantIdAndTypeAndNameAndValue(String tenantId, ExpertLabelType type,String name, String value);
}