package com.realfinance.sofa.cg.core.service.projectexec.impl;

import com.realfinance.sofa.cg.core.repository.ProjectExecutionStepRepository;
import com.realfinance.sofa.cg.core.service.projectexec.ProjectExecutionStepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 唱标
 */
public class CBProjectExecutionStepServiceImpl extends ProjectExecutionStepService {

    private static final Logger log = LoggerFactory.getLogger(CBProjectExecutionStepServiceImpl.class);

    public CBProjectExecutionStepServiceImpl(ProjectExecutionStepRepository projectExecutionStepRepository) {
        super(projectExecutionStepRepository);
    }

}
