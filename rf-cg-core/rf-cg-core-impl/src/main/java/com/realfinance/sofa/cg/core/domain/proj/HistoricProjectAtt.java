package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BasePurAtt;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_HI_PROJ_ATT")
public class HistoricProjectAtt extends BasePurAtt {

    @Column(nullable = false)
    private Integer srcId;

    @Column
    private Integer reqAttId;

    @ManyToOne
    @JoinColumn(name = "hi_proj_id", updatable = false)
    private HistoricProject project;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getReqAttId() {
        return reqAttId;
    }

    public void setReqAttId(Integer reqAttId) {
        this.reqAttId = reqAttId;
    }

    public HistoricProject getProject() {
        return project;
    }

    public void setProject(HistoricProject project) {
        this.project = project;
    }
}
