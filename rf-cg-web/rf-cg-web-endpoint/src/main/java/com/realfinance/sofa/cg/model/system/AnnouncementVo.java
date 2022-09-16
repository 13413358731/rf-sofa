package com.realfinance.sofa.cg.model.system;


import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelDto;
import com.realfinance.sofa.common.model.IEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "公告对象")
public class AnnouncementVo extends BaseVo implements  FlowableVo {


    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "暂时没用")
    private Long v;

    @Schema(description = "是否置顶")
    private Boolean isTop;

    @Schema(description = "标题")
    private String title;


    @Schema(description = "生效时间")
    private LocalDateTime takeEffectTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "公告类型")
    private String type;

    @Schema(description = "频道")
    private AnnouncementChannelVo channels;

    @Schema(description = "发布状态")
    private String releasestatus;

    @Schema(description = "发布时间")
    private LocalDateTime releaseDate;

    @Schema(description = "停用人")
    private String disabledMan;

    @Schema(description = "停用时间")
    private LocalDateTime stopDate;

    @Schema(description = "法人ID")
    private Integer tenantId;

    @Schema(description = "部门ID")
    private DepartmentVo departmentId;

    @Schema(description = "审核日期")
    private LocalDateTime passDate;

    @Schema(description = "处理状态")
    private String status;
    @Schema(description = "公告内容")
    private String content;
    @Schema(description = "备注")
    private String remarks;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AnnouncementChannelVo getChannels() {
        return channels;
    }

    public void setChannels(AnnouncementChannelVo channels) {
        this.channels = channels;
    }

    public String getReleasestatus() {
        return releasestatus;
    }

    public void setReleasestatus(String releasestatus) {
        this.releasestatus = releasestatus;
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

    public LocalDateTime getPassDate() {
        return passDate;
    }

    public void setPassDate(LocalDateTime passDate) {
        this.passDate = passDate;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return null;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
