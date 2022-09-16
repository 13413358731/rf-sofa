package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgGradeSupSumDetailsDto extends CgGradeSupSumDto {
    public CgGradeSupSumDetailsDto(Integer id, List<CgGradeSupDto> gradeSups) {
        super(id);
        this.gradeSups = gradeSups;
    }

    private List<CgGradeSupDto> gradeSups;

    public List<CgGradeSupDto> getGradeSups() {
        return gradeSups;
    }

    public void setGradeSups(List<CgGradeSupDto> gradeSups) {
        this.gradeSups = gradeSups;
    }
}
