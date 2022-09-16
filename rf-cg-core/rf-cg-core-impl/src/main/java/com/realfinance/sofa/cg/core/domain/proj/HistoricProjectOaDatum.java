package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BasePurOaDatum;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_HI_PROJ_OA_DATUM")
public class HistoricProjectOaDatum extends BasePurOaDatum {

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
