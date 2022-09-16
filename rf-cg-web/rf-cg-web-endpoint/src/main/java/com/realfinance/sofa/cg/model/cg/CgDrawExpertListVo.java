package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "专家对象")
public class CgDrawExpertListVo extends BaseVo implements FlowableVo,IdentityObject<Integer> {

    public interface Save {
    }

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "抽取方式")
    protected String drawWay;

    @Schema(description = "所属公司")
    protected String tenantId;

    @Schema(description = "所属部门")
    protected DepartmentVo expertDepartment;

    @Schema(description = "标签")
    protected String expertLabel;

    @Schema(description = "是否内部专家")
    protected Boolean IsInternalExpert;

    @Schema(description = "抽取条件")
    protected String drawItem;

    @Schema(description = "通知状态")
    protected Boolean noticeStatus;

    @Schema(description = "能否参加")
    protected Integer IsAttend;

    @Schema(description = "缺席原因")
    protected String absentReason;

    @Schema(description = "参与类型")
    protected String expertType;

    @Schema(description = "抽取的专家")
    protected CgExpertVo expert;

    @Schema(description = "流程任务")
    protected FlowInfoVo flowInfo;

    @Schema(description = "处理状态")
    protected String status;

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    public String getDrawWay() {
        return drawWay;
    }

    public void setDrawWay(String drawWay) {
        this.drawWay = drawWay;
    }


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public DepartmentVo getExpertDepartment() {
        return expertDepartment;
    }

    public void setExpertDepartment(DepartmentVo expertDepartment) {
        this.expertDepartment = expertDepartment;
    }

    public String getExpertLabel() {
        return expertLabel;
    }

    public void setExpertLabel(String expertLabel) {
        this.expertLabel = expertLabel;
    }

    public Boolean getInternalExpert() {
        return IsInternalExpert;
    }

    public void setInternalExpert(Boolean internalExpert) {
        IsInternalExpert = internalExpert;
    }

    public String getDrawItem() {
        return drawItem;
    }

    public void setDrawItem(String drawItem) {
        this.drawItem = drawItem;
    }

    public Boolean getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Boolean noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getIsAttend() {
        return IsAttend;
    }

    public void setIsAttend(Integer isAttend) {
        IsAttend = isAttend;
    }

    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason;
    }

    public String getExpertType() {
        return expertType;
    }

    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }

    /*public CgExpertVo getExpert() {
        return expert;
    }

    public void setExpert(CgExpertVo expert) {
        this.expert = expert;
    }*/

    public CgExpertVo getExpert() {
        return expert;
    }

    public void setExpert(CgExpertVo expert) {
        this.expert = expert;
    }
}
