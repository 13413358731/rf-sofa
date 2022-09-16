package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BasePurAtt;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_PROJ_ATT")
public class ProjectAtt extends BasePurAtt {

    /**
     * 采购申请附件ID
     */
    @Column
    private Integer reqAttId;

    @ManyToOne
    @JoinColumn(name = "proj_id", updatable = false)
    private Project project;

    public Integer getReqAttId() {
        return reqAttId;
    }

    public void setReqAttId(Integer reqAttId) {
        this.reqAttId = reqAttId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
