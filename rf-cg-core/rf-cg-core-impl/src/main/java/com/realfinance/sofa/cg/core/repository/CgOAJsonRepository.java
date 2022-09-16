package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.oa.OaAttachment;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface CgOAJsonRepository extends JpaRepositoryImplementation<OaAttachment, String> {
}

