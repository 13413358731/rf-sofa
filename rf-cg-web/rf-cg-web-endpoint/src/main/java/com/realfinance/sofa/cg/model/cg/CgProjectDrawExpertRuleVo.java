package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.system.DepartmentVo;

public class CgProjectDrawExpertRuleVo {

    private Integer id;

    protected DepartmentVo expertDept;

    protected CgExpertLabelVo expertLabel;

    protected Integer expertCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExpertDeptName() {
        return expertDept == null ? null : expertDept.getName();
    }

    public DepartmentVo getExpertDept() {
        return expertDept;
    }

    public void setExpertDept(DepartmentVo expertDept) {
        this.expertDept = expertDept;
    }


    public String getExpertLabelName() {
        return expertLabel == null ? null : expertLabel.getName();
    }

    public CgExpertLabelVo getExpertLabel() {
        return expertLabel;
    }

    public void setExpertLabel(CgExpertLabelVo expertLabel) {
        this.expertLabel = expertLabel;
    }

    public Integer getExpertCount() {
        return expertCount;
    }

    public void setExpertCount(Integer expertCount) {
        this.expertCount = expertCount;
    }
}
