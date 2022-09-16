package com.realfinance.sofa.cg.model.cg;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "供应商考核指标")
public class CgSupplierEvaluationSheetSubVo {

    public interface Save {
    }
    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "考核项编号")
    protected String assessmentNo;

    @Schema(description = "考核项名称")
    protected String assessmentName;

    @Schema(description = "考核项权重")
    protected Integer assessmentWeight;

    @Schema(description = "备注")
    protected String subComment;

    @Schema(description = "供应商评估样表孙子表")
    protected List<CgSupplierEvaluationSheetGrandsonVo> supplierEvaluationSheetGrandsons;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getAssessmentWeight() {
        return assessmentWeight;
    }

    public void setAssessmentWeight(Integer assessmentWeight) {
        this.assessmentWeight = assessmentWeight;
    }

    public String getSubComment() {
        return subComment;
    }

    public void setSubComment(String subComment) {
        this.subComment = subComment;
    }

    public List<CgSupplierEvaluationSheetGrandsonVo> getSupplierEvaluationSheetGrandsons() {
        return supplierEvaluationSheetGrandsons;
    }

    public void setSupplierEvaluationSheetGrandsons(List<CgSupplierEvaluationSheetGrandsonVo> supplierEvaluationSheetGrandsons) {
        this.supplierEvaluationSheetGrandsons = supplierEvaluationSheetGrandsons;
    }
}
