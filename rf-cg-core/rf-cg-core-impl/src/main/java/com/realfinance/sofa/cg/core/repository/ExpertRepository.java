package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.Expert;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ExpertRepository extends JpaRepositoryImplementation<Expert, Integer> {

    boolean existsById(String id);

    boolean existsByMemberCode(Integer membercode);
//    boolean existsByExpertAndValid(Expert expert, Boolean valid);

}
