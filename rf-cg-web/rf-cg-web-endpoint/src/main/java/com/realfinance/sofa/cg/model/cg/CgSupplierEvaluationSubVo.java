package com.realfinance.sofa.cg.model.cg;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "供应商考核指标")
public class CgSupplierEvaluationSubVo {

    public interface Save {
    }

    @Schema(description = "考核项编号")
    protected String assessmentNo;

    @Schema(description = "考核项名称")
    protected String assessmentName;

    @Schema(description = "考核项权重")
    protected Integer assessmentWeight;

    @Schema(description = "考核指标编号")
    protected String assessmentIndicatorNo;

    @Schema(description = "考核指标名称")
    protected String assessmentIndicatorName;

    @Schema(description = "计算方法")
    protected String calculation;

    @Schema(description = "指标权重")
    protected Integer indicatorWeight;

    @Schema(description = "得分")
    protected Integer score;

    public String getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(String assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
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

    public Integer getAssessmentWeight() {
        return assessmentWeight;
    }

    public void setAssessmentWeight(Integer assessmentWeight) {
        this.assessmentWeight = assessmentWeight;
    }

    public Integer getIndicatorWeight() {
        return indicatorWeight;
    }

    public void setIndicatorWeight(Integer indicatorWeight) {
        this.indicatorWeight = indicatorWeight;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
