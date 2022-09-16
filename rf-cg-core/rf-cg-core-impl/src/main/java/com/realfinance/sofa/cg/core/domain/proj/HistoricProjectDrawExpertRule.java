package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BaseDrawExpertRule;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_HI_PROJ_DRAW_EXPERT_RULE")
public class HistoricProjectDrawExpertRule extends BaseDrawExpertRule {

    @Column(nullable = false)
    private Integer srcId;

    @ManyToOne
    @JoinColumn(name = "hi_proj_id", updatable = false)
    private HistoricProject project;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public HistoricProject getProject() {
        return project;
    }

    public void setProject(HistoricProject project) {
        this.project = project;
    }
}
