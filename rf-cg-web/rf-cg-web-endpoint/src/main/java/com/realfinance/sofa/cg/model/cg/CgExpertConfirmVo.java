package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "专家对象")
public class CgExpertConfirmVo extends BaseVo implements FlowableVo, IdentityObject<Integer> {
    public interface Save {
    }

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "专家抽取事由")
    private String event;

    @Schema(description = "会议类别")
    private String sort;

    @Schema(description = "通知内容")
    private String noticeContent;

    @Schema(description = "不参加原因")
    private String absentReason;

    @Schema(description = "发送机构")
    private String tenantId;

    @Schema(description = "发送部门")
    private DepartmentVo departmentId;

    @Schema(description = "发送人")
    private UserVo drawer;

    @Schema(description = "确认状态")
    private Integer confirmStatus;

    @Schema(description = "确认时间")
    private LocalDateTime confirmTime;

    @Schema(description = "通知的专家(使用userId)")
    private UserVo expertUserId;

    @Schema(description = "专家抽取列表Id")
    private Integer expertListId;

    @Schema(description = "项目编号")
    private Integer projectId;

    @Schema(description = "采购方案")
    private CgProjectVo cgProjectVo;

    @Schema(description = "处理状态")
    protected String status;

    @Schema(description = "流程任务")

    protected FlowInfoVo flowInfo;


    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    @Override
    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason;
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

    public UserVo getDrawer() {
        return drawer;
    }

    public void setDrawer(UserVo drawer) {
        this.drawer = drawer;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public LocalDateTime getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(LocalDateTime confirmTime) {
        this.confirmTime = confirmTime;
    }

    public UserVo getExpertUserId() {
        return expertUserId;
    }

    public void setExpertUserId(UserVo expertUserId) {
        this.expertUserId = expertUserId;
    }

    public Integer getExpertListId() {
        return expertListId;
    }

    public void setExpertListId(Integer expertListId) {
        this.expertListId = expertListId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public CgProjectVo getCgProjectVo() {
        return cgProjectVo;
    }

    public void setCgProjectVo(CgProjectVo cgProjectVo) {
        this.cgProjectVo = cgProjectVo;
    }
}
