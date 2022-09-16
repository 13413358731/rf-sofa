package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class SupplierAnnouncementSaveDto implements Serializable {
    private Integer id;
    private Long v;
    private Boolean isTop;
    private String title;
    private LocalDateTime takeEffectTime;
    private LocalDateTime endTime;
    private String type;
    private Integer channels;
    private String releasestatus;
    private String status;
    private String content;
    private String remarks;

    private Integer createdUserId;

    private LocalDateTime createdTime;
    @NotEmpty
    protected  List<SupplierAnnouncementAttachmentDto> attachments;


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

    public Integer getChannels() {
        return channels;
    }

    public void setChannels(Integer channels) {
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

    public List<SupplierAnnouncementAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SupplierAnnouncementAttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
