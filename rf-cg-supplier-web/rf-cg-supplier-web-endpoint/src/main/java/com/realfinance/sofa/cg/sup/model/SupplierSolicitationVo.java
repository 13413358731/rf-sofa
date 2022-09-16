package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class SupplierSolicitationVo {

    protected Integer id;
    protected String documentNumber;
    protected String title;
    protected String content;
    protected String status;
    protected Integer Editor;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String releaseStatus;
    protected List<CgSupplierLabelDto> label;


    protected List<AttachmentVo> attachments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEditor() {
        return Editor;
    }

    public void setEditor(Integer editor) {
        Editor = editor;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<CgSupplierLabelDto> getLabel() {
        return label;
    }

    public void setLabel(List<CgSupplierLabelDto> label) {
        this.label = label;
    }

    public List<AttachmentVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentVo> attachments) {
        this.attachments = attachments;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public boolean getEnabled() {
        if (startTime == null && endTime == null) {
            return true;
        } else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = startTime == null ? LocalDateTime.MIN : startTime;
            LocalDateTime end = endTime == null ? LocalDateTime.MAX : endTime;
            return now.isBefore(start) || now.isAfter(end);
        }
    }
}
