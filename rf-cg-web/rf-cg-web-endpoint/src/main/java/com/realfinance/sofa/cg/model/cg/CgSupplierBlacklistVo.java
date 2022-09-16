package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "供应商黑名单对象")
public class CgSupplierBlacklistVo extends BaseVo implements IdentityObject<Integer>, FlowableVo {

    public interface Save { }

    @Schema(description = "ID")
    protected Integer id;

    @NotNull(groups = Save.class)
    @Schema(description = "供应商")
    protected CgSupplierVo supplier;

    @NotBlank(groups = Save.class)
    @Schema(description = "标题")
    protected String title;

    @NotBlank(groups = Save.class)
    @Schema(description = "黑名单原因")
    protected String reason;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "是否生效", accessMode = Schema.AccessMode.READ_ONLY)
    protected Boolean valid;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "处理状态", accessMode = Schema.AccessMode.READ_ONLY)
    protected String status;

    @Schema(description = "附件")
    protected List<CgSupplierBlacklistAttachmentVo> attachments=new ArrayList<>();

    protected FlowInfoVo flowInfo;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierVo supplier) {
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CgSupplierBlacklistAttachmentVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgSupplierBlacklistAttachmentVo> attachments) {
        this.attachments = attachments;
    }

    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }
}
