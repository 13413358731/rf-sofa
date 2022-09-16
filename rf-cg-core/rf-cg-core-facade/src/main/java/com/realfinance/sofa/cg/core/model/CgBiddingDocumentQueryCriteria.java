package com.realfinance.sofa.cg.core.model;

public class CgBiddingDocumentQueryCriteria {

    /**
     * 方案名称模糊
     */
    private String nameLike;

    /**
     * 项目编码模糊
     */
    private String projectNoLike;

    /**
     * 项目编码模糊
     */
    private Integer executionId;

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getProjectNoLike() {
        return projectNoLike;
    }

    public void setProjectNoLike(String projectNoLike) {
        this.projectNoLike = projectNoLike;
    }

    public Integer getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Integer executionId) {
        this.executionId = executionId;
    }
}
