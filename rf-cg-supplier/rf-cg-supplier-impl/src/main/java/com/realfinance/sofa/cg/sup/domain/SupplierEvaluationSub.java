package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 供应商评估子表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EVALUATION_SUB")
public class SupplierEvaluationSub extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *考核项编号
     */
    @Column(nullable = false)
    private String assessmentNo;

    /**
     *考核项名称
     */
    @Column(nullable = false)
    private String assessmentName;

    /**
     *考核项权重
     */
    @Column(nullable = false)
    private Integer assessmentWeight;

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

    /**
     *得分
     */
    @Column()
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "supplierEvaluationMain_id", updatable = false)
    private SupplierEvaluationMain supplierEvaluationMain;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public SupplierEvaluationMain getSupplierEvaluationMain() {
        return supplierEvaluationMain;
    }

    public void setSupplierEvaluationMain(SupplierEvaluationMain supplierEvaluationMain) {
        this.supplierEvaluationMain = supplierEvaluationMain;
    }
}

