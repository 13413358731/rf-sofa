package com.realfinance.sofa.cg.core.model;

public class CgProjectExecutionQueryCriteria {
    /**
     * 采购方案Id
     */
    private Integer projectId;

    /**
     * 项目名称
     * @return
     */
    private String nameLike;

    private Boolean returnReq;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Boolean getReturnReq() {
        return returnReq;
    }

    public void setReturnReq(Boolean returnReq) {
        this.returnReq = returnReq;
    }
}
