package com.realfinance.sofa.cg.model.cg;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "供应商考核指标")
public class CgSupplierAssessmentIndicatorVo {

    public interface Save {
    }

    @Schema(description = "考核指标编号")
    protected String assessmentIndicatorNo;

    @Schema(description = "考核指标名称")
    protected String assessmentIndicatorName;

    @Schema(description = "计算方法")
    protected String calculation;

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
