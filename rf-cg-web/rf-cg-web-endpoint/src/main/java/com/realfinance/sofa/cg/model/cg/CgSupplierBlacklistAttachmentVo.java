package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "供应商黑名单-附件对象")
public class CgSupplierBlacklistAttachmentVo extends BaseVo implements IdentityObject<Integer> {

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "附件名称")
    private String name;

    @Schema(description = "上传时间")
    private LocalDateTime uploadTime;

    @Schema(description = "文件大小")
    private Long size;

    @Schema(description = "扩展名")
    private String ext;

    @Schema(description = "文件路径")
    private String path;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "下载链接")
    protected String link;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "上传人")
    protected Integer uploader;

    @Schema(description = "附件分类")
    protected String category;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getUploader() {
        return uploader;
    }

    public void setUploader(Integer uploader) {
        this.uploader = uploader;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
