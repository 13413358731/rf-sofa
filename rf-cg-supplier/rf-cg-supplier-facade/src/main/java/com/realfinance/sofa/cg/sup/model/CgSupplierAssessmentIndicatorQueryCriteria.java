package com.realfinance.sofa.cg.sup.model;

public class CgSupplierAssessmentIndicatorQueryCriteria {

    /**
     * id查询
     */
    private String id;

    /**
     * 供应商考核Id
     */
    private Integer assessmentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }
}
