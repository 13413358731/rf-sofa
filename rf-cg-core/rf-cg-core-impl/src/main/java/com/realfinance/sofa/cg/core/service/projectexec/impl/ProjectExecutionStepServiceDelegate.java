package com.realfinance.sofa.cg.core.service.projectexec.impl;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStep;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStepType;
import com.realfinance.sofa.cg.core.repository.ProjectExecutionStepRepository;
import com.realfinance.sofa.cg.core.service.projectexec.ProjectExecutionStepService;
import com.realfinance.sofa.common.util.SpringContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.businessException;

/**
 * 采购方案执行环节代理
 * 各种类型的环节实现注入spring bean名称需要符合规范： projectExecution-{ProjectExecutionStepType}
 *
 */
@Service
@Primary
public class ProjectExecutionStepServiceDelegate extends ProjectExecutionStepService {

    protected ProjectExecutionStepServiceDelegate(ProjectExecutionStepRepository projectExecutionStepRepository) {
        super(projectExecutionStepRepository);
    }

    @Override
    public void initStep(Integer projectExecutionStepId) {
        ProjectExecutionStep projectExecutionStep = getProjectExecutionStep(projectExecutionStepId);
        ProjectExecutionStepService service = getDelegate(projectExecutionStep.getType());
        service.initStep(projectExecutionStepId);
    }

    @Override
    public void startStep(Integer projectExecutionStepId) {
        ProjectExecutionStep projectExecutionStep = getProjectExecutionStep(projectExecutionStepId);
        ProjectExecutionStepService service = getDelegate(projectExecutionStep.getType());
        service.startStep(projectExecutionStepId);
    }

    @Override
    public void endStep(Integer projectExecutionStepId) {
        ProjectExecutionStep projectExecutionStep = getProjectExecutionStep(projectExecutionStepId);
        ProjectExecutionStepService service = getDelegate(projectExecutionStep.getType());
        service.endStep(projectExecutionStepId);
    }

    protected ProjectExecutionStepService getDelegate(ProjectExecutionStepType type) {
        Objects.requireNonNull(type);
        try {
            ProjectExecutionStepService projectExecutionStepService = SpringContextHolder.getApplicationContext()
                    .getBean("projectExecution-" + type.name(), ProjectExecutionStepService.class);
            return projectExecutionStepService;
        } catch (BeansException e) {
            throw businessException("找不到执行服务");
        }
    }
}
