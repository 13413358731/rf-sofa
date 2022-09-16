package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDateTime;
import java.util.List;

public class SupplierAnnouncementDto extends BaseDto  {


    private Integer id;
    private Long v;
    private Boolean isTop;
    private String title;
    private LocalDateTime takeEffectTime;
    private LocalDateTime endTime;
    private String type;
    private SupplierAnnouncementChannelDto channels;
    private String releasestatus;
    private LocalDateTime releaseDate;
    private String disabledMan;
    private LocalDateTime stopDate;
    private String tenantId;
    private Integer departmentId;
    private LocalDateTime passDate;
    private String status;
    private String content;
    private String remarks;

    private List<SupplierAnnouncementAttachmentDto> attachments;

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

    public SupplierAnnouncementChannelDto getChannels() {
        return channels;
    }

    public void setChannels(SupplierAnnouncementChannelDto channels) {
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDateTime getPassDate() {
        return passDate;
    }

    public void setPassDate(LocalDateTime passDate) {
        this.passDate = passDate;
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
}
