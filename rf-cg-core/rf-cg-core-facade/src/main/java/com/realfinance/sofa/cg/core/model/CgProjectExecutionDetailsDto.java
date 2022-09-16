package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgProjectExecutionDetailsDto extends CgProjectExecutionDto {

    public CgProjectExecutionDetailsDto(Integer id, List<CgProjectExecutionStepDto> projectExecutionSteps, List<CgProjectExecutionSupDto> projectExecutionSups, List<CgProjectExecutionAttDto> projectExecutionAtts) {
        super(id);
        this.projectExecutionSteps = projectExecutionSteps;
        this.projectExecutionSups = projectExecutionSups;
        this.projectExecutionAtts = projectExecutionAtts;
    }

    private List<CgProjectExecutionStepDto> projectExecutionSteps;

    private List<CgProjectExecutionSupDto> projectExecutionSups;

    private List<CgProjectExecutionAttDto> projectExecutionAtts;

    public List<CgProjectExecutionStepDto> getProjectExecutionSteps() {
        return projectExecutionSteps;
    }

    public void setProjectExecutionSteps(List<CgProjectExecutionStepDto> projectExecutionSteps) {
        this.projectExecutionSteps = projectExecutionSteps;
    }

    public List<CgProjectExecutionSupDto> getProjectExecutionSups() {
        return projectExecutionSups;
    }

    public void setProjectExecutionSups(List<CgProjectExecutionSupDto> projectExecutionSups) {
        this.projectExecutionSups = projectExecutionSups;
    }

    public List<CgProjectExecutionAttDto> getProjectExecutionAtts() {
        return projectExecutionAtts;
    }

    public void setProjectExecutionAtts(List<CgProjectExecutionAttDto> projectExecutionAtts) {
        this.projectExecutionAtts = projectExecutionAtts;
    }
}
