package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupplierBlacklistSaveDto {

    private Integer id;

    private Integer supplier;

    private String title;

    private String reason;

    private List<CgAttachmentDto> attachments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<CgAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttachmentDto> attachments) {
        this.attachments = attachments;
    }
}
