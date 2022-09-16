package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupplierEvaluationSheetSubDto extends BaseDto {

    private Integer id;

    /**
     *考核项编号
     */
    private String assessmentNo;

    /**
     *考核项名称
     */
    private String assessmentName;

    /**
     * 考核项权重
     */
    private Integer assessmentWeight;

    /**
     *备注
     */
    private String subComment;

    /**
     *供应商评估样表孙子表
     */
    protected List<CgSupplierEvaluationSheetGrandsonDto> supplierEvaluationSheetGrandsons;

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

    public List<CgSupplierEvaluationSheetGrandsonDto> getSupplierEvaluationSheetGrandsons() {
        return supplierEvaluationSheetGrandsons;
    }

    public void setSupplierEvaluationSheetGrandsons(List<CgSupplierEvaluationSheetGrandsonDto> supplierEvaluationSheetGrandsons) {
        this.supplierEvaluationSheetGrandsons = supplierEvaluationSheetGrandsons;
    }
}
