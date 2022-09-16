package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "供应商考核指标")
public class CgSupplierEvaluationDepartmentVo {

    public interface Save {
    }

    @Schema(description = "制单人部门")
    protected DepartmentVo departmentIdSub;

    @Schema(description = "权重")
    protected Integer weight;

//    @Schema(description = "评估人")
//    protected UserVo evaluator;

    @Schema(description = "供应商评估子表")
    protected List<CgSupplierEvaluatorVo> supplierEvaluators;

    public DepartmentVo getDepartmentIdSub() {
        return departmentIdSub;
    }

    public void setDepartmentIdSub(DepartmentVo departmentIdSub) {
        this.departmentIdSub = departmentIdSub;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<CgSupplierEvaluatorVo> getSupplierEvaluators() {
        return supplierEvaluators;
    }

    public void setSupplierEvaluators(List<CgSupplierEvaluatorVo> supplierEvaluators) {
        this.supplierEvaluators = supplierEvaluators;
    }
}
