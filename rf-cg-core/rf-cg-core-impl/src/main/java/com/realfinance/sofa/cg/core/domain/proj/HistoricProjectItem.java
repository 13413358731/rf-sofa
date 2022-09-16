package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BasePurItem;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_HI_PROJ_ITEM")
public class HistoricProjectItem extends BasePurItem {

    @Column(nullable = false)
    private Integer srcId;

    /**
     * 原采购申请清单ID
     */
    @Column
    protected Integer reqItemId;

    @ManyToOne
    @JoinColumn(name = "hi_proj_id", updatable = false)
    private HistoricProject project;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getReqItemId() {
        return reqItemId;
    }

    public void setReqItemId(Integer reqItemId) {
        this.reqItemId = reqItemId;
    }

    public HistoricProject getProject() {
        return project;
    }

    public void setProject(HistoricProject project) {
        this.project = project;
    }

}
