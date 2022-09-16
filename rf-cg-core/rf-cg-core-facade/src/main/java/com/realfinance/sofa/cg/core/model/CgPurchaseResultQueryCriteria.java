package com.realfinance.sofa.cg.core.model;

public class CgPurchaseResultQueryCriteria {
    /**
     * 采购方案Id
     */
    private Integer projectId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目编号
     */
    private String projectNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
