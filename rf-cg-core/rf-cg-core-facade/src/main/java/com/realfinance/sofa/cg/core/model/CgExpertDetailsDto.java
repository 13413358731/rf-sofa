package com.realfinance.sofa.cg.core.model;

import java.util.List;
import java.util.Set;

public class CgExpertDetailsDto extends CgExpertDto {
    protected Set<CgExpertLabelSmallDto> expertLabels;

    public CgExpertDetailsDto(Integer id) {
        super(id);
    }

    public Set<CgExpertLabelSmallDto> getExpertLabels() {
        return expertLabels;
    }

    public void setExpertLabels(Set<CgExpertLabelSmallDto> expertLabels) {
        this.expertLabels = expertLabels;
    }
}
