package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "供应商评估主表")
public class CgSupEvaluationProcessMngVo extends BaseVo implements IdentityObject<Integer> {
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
    protected CgSupplierEvaluationSheetMainVo evaluationSheetNo;

    @Schema(description = "采购方案")
    protected CgProjectVo projectId;

    @Schema(description = "供应商对象")
    protected CgSupplierVo supplierId;

    @Schema(description = "评估发起日期")
    private LocalDate evaluationStartDate;

    @Schema(description = "评估结束日期")
    private LocalDate evaluationEndDate;

    @Schema(description = "评估状态")
    private Integer evaluationStatus;

    @Schema(description = "评估分数")
    private float evaluationScore;

    @Schema(description = "评估等级")
    private Integer evaluationLevel;

    @Schema(description = "统计时间")
    private LocalDateTime statisticalTime;

    @Schema(description = "发布日期")
    private LocalDate releaseDate;

    @Schema(description = "发布状态")
    private Integer releaseStatus;

    @Schema(description = "供应商评估子表")
    protected List<CgSupplierEvaluationDepartmentVo> supplierEvaluationDepartments;

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

    public CgSupplierEvaluationSheetMainVo getEvaluationSheetNo() {
        return evaluationSheetNo;
    }

    public void setEvaluationSheetNo(CgSupplierEvaluationSheetMainVo evaluationSheetNo) {
        this.evaluationSheetNo = evaluationSheetNo;
    }

    public CgProjectVo getProjectId() {
        return projectId;
    }

    public void setProjectId(CgProjectVo projectId) {
        this.projectId = projectId;
    }

    public CgSupplierVo getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(CgSupplierVo supplierId) {
        this.supplierId = supplierId;
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

    public Integer getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(Integer evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public float getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(float evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public Integer getEvaluationLevel() {
        return evaluationLevel;
    }

    public void setEvaluationLevel(Integer evaluationLevel) {
        this.evaluationLevel = evaluationLevel;
    }

    public LocalDateTime getStatisticalTime() {
        return statisticalTime;
    }

    public void setStatisticalTime(LocalDateTime statisticalTime) {
        this.statisticalTime = statisticalTime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public List<CgSupplierEvaluationDepartmentVo> getSupplierEvaluationDepartments() {
        return supplierEvaluationDepartments;
    }

    public void setSupplierEvaluationDepartments(List<CgSupplierEvaluationDepartmentVo> supplierEvaluationDepartments) {
        this.supplierEvaluationDepartments = supplierEvaluationDepartments;
    }
}
