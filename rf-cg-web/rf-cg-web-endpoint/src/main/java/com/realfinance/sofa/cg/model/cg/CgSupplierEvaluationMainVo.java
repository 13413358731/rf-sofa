package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "供应商评估主表")
public class CgSupplierEvaluationMainVo extends BaseVo implements IdentityObject<Integer> {
    public interface Save {
    }

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "法人ID")
    protected String tenantId;

    @Schema(description = "制单人部门")
    protected DepartmentVo departmentId;

    @Schema(description = "评估发起编号")
    protected String evaluationStartNo;

    @Schema(description = "评估发起名称")
    protected String evaluationStartName;

    @Schema(description = "评估表编号")
    protected String evaluationSheetNo;

    @Schema(description = "评估表名称")
    protected String evaluationSheetName;

    @Schema(description = "评估开始日期")
    protected LocalDate evaluationStartDate;

    @Schema(description = "评估结束日期")
    protected LocalDate evaluationEndDate;

    @Schema(description = "评估人")
    protected Integer evaluator;

    @Schema(description = "发布日期")
    protected LocalDate releaseDate;

    @Schema(description = "完成状态")
    protected Integer status;

    @Schema(description = "完成日期")
    protected LocalDate finishDate;

    @Schema(description = "供应商评估子表")
    protected List<CgSupplierEvaluationSubVo> supplierEvaluationSubs;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public DepartmentVo getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(DepartmentVo departmentId) {
        this.departmentId = departmentId;
    }

    public String getEvaluationStartNo() {
        return evaluationStartNo;
    }

    public void setEvaluationStartNo(String evaluationStartNo) {
        this.evaluationStartNo = evaluationStartNo;
    }

    public String getEvaluationStartName() {
        return evaluationStartName;
    }

    public void setEvaluationStartName(String evaluationStartName) {
        this.evaluationStartName = evaluationStartName;
    }

    public String getEvaluationSheetNo() {
        return evaluationSheetNo;
    }

    public void setEvaluationSheetNo(String evaluationSheetNo) {
        this.evaluationSheetNo = evaluationSheetNo;
    }

    public String getEvaluationSheetName() {
        return evaluationSheetName;
    }

    public void setEvaluationSheetName(String evaluationSheetName) {
        this.evaluationSheetName = evaluationSheetName;
    }

    public LocalDate getEvaluationStartDate() {
        return evaluationStartDate;
    }

    public void setEvaluationStartDate(LocalDate evaluationStartDate) {
        this.evaluationStartDate = evaluationStartDate;
    }

    public LocalDate getEvaluationEndDate() {
        return evaluationEndDate;
    }

    public void setEvaluationEndDate(LocalDate evaluationEndDate) {
        this.evaluationEndDate = evaluationEndDate;
    }

    public Integer getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Integer evaluator) {
        this.evaluator = evaluator;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public List<CgSupplierEvaluationSubVo> getSupplierEvaluationSubs() {
        return supplierEvaluationSubs;
    }

    public void setSupplierEvaluationSubs(List<CgSupplierEvaluationSubVo> supplierEvaluationSubs) {
        this.supplierEvaluationSubs = supplierEvaluationSubs;
    }
}
