package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 供应商评估样表子表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EVALUATION_SHEET_GRANDSON")
public class SupplierEvaluationSheetGrandson extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *考核指标编号
     */
    @Column(nullable = false)
    private String assessmentIndicatorNo;

    /**
     *考核指标名称
     */
    @Column(nullable = false)
    private String assessmentIndicatorName;

    /**
     *计算方法
     */
    @Column(nullable = false)
    private String calculation;

    /**
     *指标权重
     */
    @Column(nullable = false)
    private Integer indicatorWeight;

    @ManyToOne
    @JoinColumn(name = "supplierEvaluationSheetSub_id", updatable = false)
    private SupplierEvaluationSheetSub supplierEvaluationSheetSub;

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

    public Integer getIndicatorWeight() {
        return indicatorWeight;
    }

    public void setIndicatorWeight(Integer indicatorWeight) {
        this.indicatorWeight = indicatorWeight;
    }

    public SupplierEvaluationSheetSub getSupplierEvaluationSheetSub() {
        return supplierEvaluationSheetSub;
    }

    public void setSupplierEvaluationSheetSub(SupplierEvaluationSheetSub supplierEvaluationSheetSub) {
        this.supplierEvaluationSheetSub = supplierEvaluationSheetSub;
    }
}

