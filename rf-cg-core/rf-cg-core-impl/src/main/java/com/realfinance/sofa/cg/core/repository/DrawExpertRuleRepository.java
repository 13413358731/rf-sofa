package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.DrawExpertRule;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface DrawExpertRuleRepository extends JpaRepositoryImplementation<DrawExpertRule, Integer> {

    boolean existsById(String id);

}
