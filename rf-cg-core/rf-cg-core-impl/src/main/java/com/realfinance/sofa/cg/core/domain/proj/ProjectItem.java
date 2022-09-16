package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BasePurItem;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_PROJ_ITEM")
public class ProjectItem extends BasePurItem {
                                                                                                                                                                                                                                                                                             
    /**
     * 原采购申请清单ID
     */
    @Column
    protected Integer reqItemId;

    @ManyToOne
    @JoinColumn(name = "proj_id",updatable = false)
    private Project project;

    public Integer getReqItemId() {
        return reqItemId;
    }

    public void setReqItemId(Integer reqItemId) {
        this.reqItemId = reqItemId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
