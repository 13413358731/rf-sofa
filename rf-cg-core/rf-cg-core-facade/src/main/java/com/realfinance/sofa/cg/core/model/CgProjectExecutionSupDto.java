package com.realfinance.sofa.cg.core.model;

public class CgProjectExecutionSupDto {

    protected Integer id;

    /**
     * 供应商ID
     */
    protected Integer supplierId;

    /**
     * 联系人姓名
     */
    protected String contactName;

    /**
     * 联系人手机
     */
    protected String contactMobile;

    /**
     * 联系人邮箱
     */
    protected String contactEmail;

    /**
     * 推荐理由
     */
    protected String reason;

    /**
     * 备注
     */
    protected String note;

    /**
     * 来源
     */
    protected String source;

    private String modifyMode;

    /**
     * 变更时环节
     */
    private String modifyStep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getModifyMode() {
        return modifyMode;
    }

    public void setModifyMode(String modifyMode) {
        this.modifyMode = modifyMode;
    }

    public String getModifyStep() {
        return modifyStep;
    }

    public void setModifyStep(String modifyStep) {
        this.modifyStep = modifyStep;
    }
}
