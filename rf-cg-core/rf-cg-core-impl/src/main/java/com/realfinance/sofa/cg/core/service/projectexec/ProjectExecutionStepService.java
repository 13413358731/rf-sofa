package com.realfinance.sofa.cg.core.service.projectexec;

import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStep;
import com.realfinance.sofa.cg.core.repository.ProjectExecutionStepRepository;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.entityNotFound;

public abstract class ProjectExecutionStepService {

    protected final ProjectExecutionStepRepository projectExecutionStepRepository;

    protected ProjectExecutionStepService(ProjectExecutionStepRepository projectExecutionStepRepository) {
        this.projectExecutionStepRepository = projectExecutionStepRepository;
    }

    /**
     * 初始化环节
     * @param projectExecutionStepId
     */
    public void initStep(Integer projectExecutionStepId) {

    }

    /**
     * 开始环节
     * @param projectExecutionStepId
     */
    public void startStep(Integer projectExecutionStepId) {
        ProjectExecutionStep projectExecutionStep = getProjectExecutionStep(projectExecutionStepId);
        projectExecutionStep.setStartTime(LocalDateTime.now());
        projectExecutionStepRepository.save(projectExecutionStep);
    }

    /**
     * 结束环节
     * @param projectExecutionStepId
     */
    public void endStep(Integer projectExecutionStepId) {
        ProjectExecutionStep projectExecutionStep = getProjectExecutionStep(projectExecutionStepId);
        projectExecutionStep.setEndTime(LocalDateTime.now());
        projectExecutionStepRepository.save(projectExecutionStep);
    }

    protected ProjectExecutionStep getProjectExecutionStep(Integer projectExecutionStepId) {
        return projectExecutionStepRepository.findById(Objects.requireNonNull(projectExecutionStepId))
                .orElseThrow(() -> entityNotFound(ProjectExecutionStep.class, "id", projectExecutionStepId));
    }
}
