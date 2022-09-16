package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 供应商评估样表子表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EVALUATION_SHEET_SUB")
public class SupplierEvaluationSheetSub extends BaseEntity implements IEntity<Integer> {

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
     *备注
     */
    @Column()
    private String subComment;

    @ManyToOne
    @JoinColumn(name = "supplierEvaluationSheetMain_id", updatable = false)
    private SupplierEvaluationSheetMain supplierEvaluationSheetMain;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplierEvaluationSheetSub_id")
    private List<SupplierEvaluationSheetGrandson> supplierEvaluationSheetGrandsons;

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

    public SupplierEvaluationSheetMain getSupplierEvaluationSheetMain() {
        return supplierEvaluationSheetMain;
    }

    public void setSupplierEvaluationSheetMain(SupplierEvaluationSheetMain supplierEvaluationSheetMain) {
        this.supplierEvaluationSheetMain = supplierEvaluationSheetMain;
    }

    public List<SupplierEvaluationSheetGrandson> getSupplierEvaluationSheetGrandsons() {
        return supplierEvaluationSheetGrandsons;
    }

    public void setSupplierEvaluationSheetGrandsons(List<SupplierEvaluationSheetGrandson> supplierEvaluationSheetGrandsons) {
        this.supplierEvaluationSheetGrandsons = supplierEvaluationSheetGrandsons;
    }
}

