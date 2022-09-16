package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.domain.DrawExpertList;
import com.realfinance.sofa.cg.core.domain.Expert;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface DrawExpertListRepository extends JpaRepositoryImplementation<DrawExpertList, Integer> {

    boolean existsById(String id);

    DrawExpertList findByExpert(Expert expert);

    /**
     * 根据专家标签和部门Id确定
     * @param labels
     * @param departmentId
     * @return
     */
//    List<Expert> findByExpertLabelAndNumber(Set<ExpertLabel> labels, Department departmentId);

}
