package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 供应商考核指标子表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_ASSESSMENT_INDICATOR", indexes = {
        @Index(columnList = "assessmentIndicatorNo",unique = true)
})
public class SupplierAssessmentIndicator extends BaseEntity implements IEntity<Integer> {

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

    @ManyToOne
    @JoinColumn(name = "supplierAssessment_id", updatable = false)
    private SupplierAssessment supplierAssessment;

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

    public SupplierAssessment getSupplierAssessment() {
        return supplierAssessment;
    }

    public void setSupplierAssessment(SupplierAssessment supplierAssessment) {
        this.supplierAssessment = supplierAssessment;
    }
}

