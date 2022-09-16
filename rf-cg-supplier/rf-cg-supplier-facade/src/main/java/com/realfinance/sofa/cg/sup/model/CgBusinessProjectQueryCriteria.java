package com.realfinance.sofa.cg.sup.model;

import java.util.Set;

public class CgBusinessProjectQueryCriteria {

    /**
     * 供应商ID
     */
    private Integer supplierId;
    /**
     * 项目状态
     */
    private String projectStatus;
    /**
     * 项目状态
     */
    private String projectId;

    /**
     * 项目状态不等于
     */
    private Set<String> projectStatusNotIn;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Set<String> getProjectStatusNotIn() {
        return projectStatusNotIn;
    }

    public void setProjectStatusNotIn(Set<String> projectStatusNotIn) {
        this.projectStatusNotIn = projectStatusNotIn;
    }
}
