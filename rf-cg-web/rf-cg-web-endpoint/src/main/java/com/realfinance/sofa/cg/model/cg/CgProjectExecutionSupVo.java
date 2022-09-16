package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案执行-供应商对象")
public class CgProjectExecutionSupVo {
    protected Integer id;

    @Schema(description = "供应商")
    protected CgSupplierVo supplier;

    @Schema(description = "联系人姓名")
    protected String contactName;

    @Schema(description = "联系人手机")
    protected String contactMobile;

    @Schema(description = "联系人邮箱")
    protected String contactEmail;

    @Schema(description = "推荐理由")
    protected String reason;

    @Schema(description = "备注")
    protected String note;

    @Schema(description = "来源")
    protected String source;

    @Schema(description = "变更方式，XZ 新增， TT 淘汰")
    private String modifyMode;

    /**
     * 变更时环节
     */
    @Schema(description = "变更时环节")
    private String modifyStep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierVo supplier) {
        this.supplier = supplier;
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
