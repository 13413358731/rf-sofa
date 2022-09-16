package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BasePurSup;

import javax.persistence.*;

/**
 * 推荐供应商
 */
@Entity
@Table(name = "CG_CORE_HI_PROJ_SUP")
public class HistoricProjectSup extends BasePurSup {

    @Column(nullable = false)
    private Integer srcId;

    /**
     * 原采购申请推荐供应商ID
     */
    @Column
    protected Integer reqSupId;

    @ManyToOne
    @JoinColumn(name = "hi_proj_id", updatable = false)
    private HistoricProject project;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getReqSupId() {
        return reqSupId;
    }

    public void setReqSupId(Integer reqSupId) {
        this.reqSupId = reqSupId;
    }

    public HistoricProject getProject() {
        return project;
    }

    public void setProject(HistoricProject project) {
        this.project = project;
    }
}
