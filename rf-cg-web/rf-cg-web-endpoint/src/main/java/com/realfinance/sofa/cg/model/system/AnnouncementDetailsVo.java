package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelDto;
import com.realfinance.sofa.common.model.IdentityObject;
import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "公告详情对象")
public class AnnouncementDetailsVo extends BaseVo implements IdentityObject<Integer> , FlowableVo {

    @Schema(description ="ID")
    private Integer id;

    @Schema(description ="置顶")
    private Boolean isTop;

    @Schema(description ="标题")
    private String title;

    @Schema(description ="类型")
    private String type;

    @Schema(description ="状态")
    private String releasestatus;

    @Schema(description ="频道")
    private AnnouncementChannelVo channels;

    @Schema(description ="生效时间")
    private LocalDateTime takeEffectTime;

    @Schema(description ="结束时间")
    private LocalDateTime endTime;

    @Schema(description ="发布日期")
    private LocalDateTime releaseDate;

    @Schema(description ="停用人")
    private String disabledMan;

    @Schema(description ="停用日期")
    private LocalDateTime stopDate;

    @Schema(description ="制单人公司")
    private String tenantId;

    @Schema(description ="制单人部门")
    private DepartmentVo departmentId;

    @Schema(description ="审核日期")
    private LocalDateTime passDate;

    @Schema(description ="内容")
    private String content;

    @Schema(description ="备注")
    private String remarks;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "处理状态", accessMode = Schema.AccessMode.READ_ONLY)
    protected String status;

    @Schema(description = "流程任务")
    protected FlowInfoVo flowInfo;

    @Schema(description = "附件")
    private List<CgAttVo> attachments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReleasestatus() {
        return releasestatus;
    }

    public void setReleasestatus(String releasestatus) {
        this.releasestatus = releasestatus;
    }

    public AnnouncementChannelVo getChannels() {
        return channels;
    }

    public void setChannels(AnnouncementChannelVo channels) {
        this.channels = channels;
    }

    public LocalDateTime getTakeEffectTime() {
        return takeEffectTime;
    }

    public void setTakeEffectTime(LocalDateTime takeEffectTime) {
        this.takeEffectTime = takeEffectTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDisabledMan() {
        return disabledMan;
    }

    public void setDisabledMan(String disabledMan) {
        this.disabledMan = disabledMan;
    }

    public LocalDateTime getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDateTime stopDate) {
        this.stopDate = stopDate;
    }

    public DepartmentVo getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(DepartmentVo departmentId) {
        this.departmentId = departmentId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDateTime getPassDate() {
        return passDate;
    }

    public void setPassDate(LocalDateTime passDate) {
        this.passDate = passDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    public List<CgAttVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttVo> attachments) {
        this.attachments = attachments;
    }
}
