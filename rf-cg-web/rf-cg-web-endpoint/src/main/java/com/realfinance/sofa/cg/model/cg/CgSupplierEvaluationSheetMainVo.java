package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Schema(description = "供应商评估样表")
public class CgSupplierEvaluationSheetMainVo extends BaseVo implements FlowableVo,IdentityObject<Integer> {
    public interface Save {
    }

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "法人ID")
    protected String tenantId;

    @Schema(description = "制单人部门")
    protected DepartmentVo departmentId;

    @Schema(description = "评估表编号")
    protected String evaluationSheetNo;

    @Schema(description = "评估表名称")
    protected String evaluationSheetName;

    @Schema(description = "填表说明")
    private String description;

    @Schema(description = "是否生效")
    private Boolean valid;

    @Schema(description = "审批通过时间")
    private LocalDateTime passTime;

    @Schema(description = "供应商评估样表子表")
    protected List<CgSupplierEvaluationSheetSubVo> supplierEvaluationSheetSubs;

    private FlowInfoVo flowInfo;

    @Schema(description = "审批流状态")
    private String status;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CgSupplierEvaluationSheetSubVo> getSupplierEvaluationSheetSubs() {
        return supplierEvaluationSheetSubs;
    }

    public void setSupplierEvaluationSheetSubs(List<CgSupplierEvaluationSheetSubVo> supplierEvaluationSheetSubs) {
        this.supplierEvaluationSheetSubs = supplierEvaluationSheetSubs;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }
}
