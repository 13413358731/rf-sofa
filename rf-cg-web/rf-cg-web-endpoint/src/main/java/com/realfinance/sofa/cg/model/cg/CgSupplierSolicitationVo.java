package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.sup.model.CgSupplierDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelDto;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Schema(description = "供应商意向征集对象")
public class CgSupplierSolicitationVo extends BaseVo implements IdentityObject<Integer>, FlowableVo {

    @Schema(description = "id")
    private Integer id;

    @Schema(description ="单据编号")
    private String documentNumber;

    @Schema(description ="标题")
    private String title;

    @Schema(description ="内容")
    private  String content;


    @Schema(description ="标签")
    protected List<CgSupplierLabelVo>   label;


    @Schema(description ="第二编辑人")
    protected UserVo Editor;


    @Schema(description = "处理状态")
    protected String status;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "截止时间")
    private LocalDateTime endTime;


    @Schema(description = "发布状态")
    private String releaseStatus;


    @Schema(description = "流程任务")
    protected FlowInfoVo flowInfo;



    @Schema(description = "附件")
    private List<CgAttVo> attachments;


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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    public List<CgSupplierLabelVo> getLabel() {
        return label;
    }

    public void setLabel(List<CgSupplierLabelVo> label) {
        this.label = label;
    }

    public UserVo getEditor() {
        return Editor;
    }

    public void setEditor(UserVo editor) {
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

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public List<CgAttVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttVo> attachments) {
        this.attachments = attachments;
    }
}
