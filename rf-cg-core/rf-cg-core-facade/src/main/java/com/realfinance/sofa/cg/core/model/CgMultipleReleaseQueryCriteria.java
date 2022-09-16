package com.realfinance.sofa.cg.core.model;

public class CgMultipleReleaseQueryCriteria {

    /**
     * 采购方案执行ID
     */
    private Integer projectExecutionId;

    /**
     * 采购方案名称
     * @return
     */
    private String nameLike;

    public Integer getProjectExecutionId() {
        return projectExecutionId;
    }

    public void setProjectExecutionId(Integer projectExecutionId) {
        this.projectExecutionId = projectExecutionId;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }
}
