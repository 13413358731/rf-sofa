package com.realfinance.sofa.cg.sup.model;

public class CgSupplierAssessmentIndicatorDto extends BaseDto {

    private Integer id;

    /**
     *考核指标编号
     */
    private String assessmentIndicatorNo;

    /**
     *考核指标名称
     */
    private String assessmentIndicatorName;

    /**
     *计算方法
     */
    private String calculation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssessmentIndicatorNo() {
        return assessmentIndicatorNo;
    }

    public void setAssessmentIndicatorNo(String assessmentIndicatorNo) {
        this.assessmentIndicatorNo = assessmentIndicatorNo;
    }

    public String getAssessmentIndicatorName() {
        return assessmentIndicatorName;
    }

    public void setAssessmentIndicatorName(String assessmentIndicatorName) {
        this.assessmentIndicatorName = assessmentIndicatorName;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }
}
