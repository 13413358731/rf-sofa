package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "供应商考核")
public class CgSupplierAssessmentVo extends BaseVo implements IdentityObject<Integer> {
    public interface Save {
    }

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "法人ID")
    protected String tenantId;

    @Schema(description = "制单人部门")
    protected DepartmentVo departmentId;

    @Schema(description = "考核项编号")
    protected String assessmentNo;

    @Schema(description = "考核项名称")
    protected String assessmentName;

    @Schema(description = "备注")
    protected String comment;

    @Schema(description = "供应商考核指标")
    protected List<CgSupplierAssessmentIndicatorVo> supplierAssessmentIndicators;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<CgSupplierAssessmentIndicatorVo> getSupplierAssessmentIndicators() {
        return supplierAssessmentIndicators;
    }

    public void setSupplierAssessmentIndicators(List<CgSupplierAssessmentIndicatorVo> supplierAssessmentIndicators) {
        this.supplierAssessmentIndicators = supplierAssessmentIndicators;
    }
}
