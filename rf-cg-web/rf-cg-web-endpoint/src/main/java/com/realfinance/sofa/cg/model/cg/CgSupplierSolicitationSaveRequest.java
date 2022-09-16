package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Schema(description = "保存意向征集请求对象")
public class CgSupplierSolicitationSaveRequest {

    @Schema(description = "id")
    protected Integer id;


    @Schema(description = "单据编号")
    protected String documentNumber;


    @Schema(description = "标题")
    protected String title;


    @Schema(description = "内容")
    protected String content;


    @Schema(description = "标签")
    protected List<ReferenceObject<Integer>> label;



    @Schema(description = "开始时间")
    private LocalDateTime startTime;



    @Schema(description = "截止时间")
    private LocalDateTime endTime;


    @Schema(description = "处理状态")
    private String status;

//    @Schema(description = "第二编辑人")
//    private  Integer Editor;

    @Schema(description = "附件")
    private List<CgAttVo> attachments;


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

    public List<ReferenceObject<Integer>> getLabel() {
        return label;
    }

    public void setLabel(List<ReferenceObject<Integer>> label) {
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

//    public Integer getEditor() {
//        return Editor;
//    }
//
//    public void setEditor(Integer editor) {
//        Editor = editor;
//    }


    public List<CgAttVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttVo> attachments) {
        this.attachments = attachments;
    }
}
