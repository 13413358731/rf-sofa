package com.realfinance.sofa.cg.sup.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class CgSupplierSolicitationSaveDto implements Serializable {

    protected Integer id;
    protected String documentNumber;
    protected String title;
    protected String content;
    protected List<Integer> label;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected String status;
    private Integer Editor;

    protected List<SupplierSolicitationAttachmentDto> attachments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Integer> getLabel() {
        return label;
    }

    public void setLabel(List<Integer> label) {
        this.label = label;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
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

    @Override
    public String toString() {
        return "CgSupplierSolicitationSaveDto{" +
                "id=" + id +
                ", documentNumber=" + documentNumber +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", label=" + label +

                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", Editor='" + Editor + '\'' +
                '}';
    }

    public List<SupplierSolicitationAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SupplierSolicitationAttachmentDto> attachments) {
        this.attachments = attachments;
    }
}
