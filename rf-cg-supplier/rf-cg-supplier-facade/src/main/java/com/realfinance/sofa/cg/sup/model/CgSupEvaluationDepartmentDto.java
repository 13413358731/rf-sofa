package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupEvaluationDepartmentDto extends BaseDto {

    private Integer id;

    /**
     *制单人部门
     */
    private Integer departmentIdSub;

    /**
     *权重
     */
    private Integer weight;

    /**
     *评估人
     */
    protected List<CgSupEvaluatorDto> supplierEvaluators;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentIdSub() {
        return departmentIdSub;
    }

    public void setDepartmentIdSub(Integer departmentIdSub) {
        this.departmentIdSub = departmentIdSub;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<CgSupEvaluatorDto> getSupplierEvaluators() {
        return supplierEvaluators;
    }

    public void setSupplierEvaluators(List<CgSupEvaluatorDto> supplierEvaluators) {
        this.supplierEvaluators = supplierEvaluators;
    }
}

