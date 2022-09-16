package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.Parameter;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ParameterRepository extends JpaRepositoryImplementation<Parameter, Integer> {
    Parameter findByParameterCode(String parameterCode);
}
