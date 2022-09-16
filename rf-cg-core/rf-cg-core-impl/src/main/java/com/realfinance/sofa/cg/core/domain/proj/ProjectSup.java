package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BasePurSup;

import javax.persistence.*;

/**
 * 推荐供应商
 */
@Entity
@Table(name = "CG_CORE_PROJ_SUP")
public class ProjectSup extends BasePurSup {

    /**
     * 原采购申请推荐ID
     */
    @Column
    protected Integer reqSupId;

    @ManyToOne
    @JoinColumn(name = "proj_id",updatable = false)
    private Project project;

    public Integer getReqSupId() {
        return reqSupId;
    }

    public void setReqSupId(Integer reqSupId) {
        this.reqSupId = reqSupId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
