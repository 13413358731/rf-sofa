package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgProjectForExecDto extends CgProjectDto {

    private List<CgProjectEvalDto> projectEvals;

    public List<CgProjectEvalDto> getProjectEvals() {
        return projectEvals;
    }

    public void setProjectEvals(List<CgProjectEvalDto> projectEvals) {
        this.projectEvals = projectEvals;
    }

}
