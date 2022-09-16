package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "保存更新公告对象")
public class AnnouncementSaveRequest {

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
    private ReferenceObject<Integer> channels;

    @Schema(description = "发布状态")
    private String releasestatus;

    @Schema(description = "处理状态")
    private String status;
    @Schema(description = "公告内容")
    private String content;
    @Schema(description = "备注")
    private String remarks;
    @Schema(description = "附件")
    private List<CgAttVo> attachments;


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

    public ReferenceObject<Integer> getChannels() {
        return channels;
    }

    public void setChannels(ReferenceObject<Integer> channels) {
        this.channels = channels;
    }

    public String getReleasestatus() {
        return releasestatus;
    }

    public void setReleasestatus(String releasestatus) {
        this.releasestatus = releasestatus;
    }

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

    public List<CgAttVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttVo> attachments) {
        this.attachments = attachments;
    }
}
