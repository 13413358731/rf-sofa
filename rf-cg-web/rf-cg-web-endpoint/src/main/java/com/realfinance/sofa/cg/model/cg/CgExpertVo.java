package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "专家对象")
public class CgExpertVo extends BaseVo implements IdentityObject<Integer>, FlowableVo {

    public interface Save { }

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "法人ID")
    protected String tenantId;

    /**
     * 专家编码
     */
    @Schema(description = "专家编码")
    protected String expertCode;

    /**
     * 是否内部专家
     */
    @Schema(description = "是否内部专家")
    protected boolean isInternalExpert;
    /**
     * 人员编码
     */
    @Schema(description = "人员编码")
    protected UserVo memberCode;
    /**
     * 姓名
     */
    @Schema(description = "姓名")
    protected String name;
    /**
     * 工作部门
     */
    @Schema(description = "部门")
    protected DepartmentVo expertDepartment;
    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    protected String phone;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    protected String email;
    /**
     * 专家来源
     */
    @Schema(description = "专家来源")
    protected Boolean expertSource;
    /**
     * 专家类别
     */
    @Schema(description = "专家类别")
    protected String expertType;

    /**
     * 是否生效
     */
    @Schema(description = "是否生效")
    protected Boolean valid;

    /**
     * 专家状态
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "处理状态", accessMode = Schema.AccessMode.READ_ONLY)
    protected String expertStatus;

    /**
     * 专家标签
     */
    @Schema(description = "标签")
    protected List<CgExpertLabelVo> expertLabels;

    protected FlowInfoVo flowInfo;

    public String getExpertCode() {
        return expertCode;
    }

    public void setExpertCode(String expertCode) {
        this.expertCode = expertCode;
    }

    public boolean isInternalExpert() {
        return isInternalExpert;
    }

    public void setInternalExpert(boolean internalExpert) {
        isInternalExpert = internalExpert;
    }


    public UserVo getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(UserVo memberCode) {
        this.memberCode = memberCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentVo getExpertDepartment() {
        return expertDepartment;
    }

    public void setExpertDepartment(DepartmentVo expertDepartment) {
        this.expertDepartment = expertDepartment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getExpertSource() {
        return expertSource;
    }

    public void setExpertSource(Boolean expertSource) {
        this.expertSource = expertSource;
    }

    public String getExpertType() {
        return expertType;
    }

    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getExpertStatus() {
        return expertStatus;
    }

    public void setExpertStatus(String expertStatus) {
        this.expertStatus = expertStatus;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public List<CgExpertLabelVo> getExpertLabels() {
        return expertLabels;
    }

    public void setExpertLabels(List<CgExpertLabelVo> expertLabels) {
        this.expertLabels = expertLabels;
    }

    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    @Override
    public String getStatus() {
        return expertStatus;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
