package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "供应商-附件对象")
public class CgSupplierAttachmentVo extends BaseVo implements IdentityObject<Integer> {

    @Schema(description = "ID")
    protected Integer id;
    @Schema(description = "附件分类")
    protected String category;
    @Schema(description = "附件名称")
    protected String name;
    @Schema(description = "上传时间")
    protected LocalDateTime uploadTime;
    @Schema(description = "文件大小")
    protected Long size;
    @Schema(description = "扩展名")
    protected String ext;
    @Schema(description = "文件路径")
    protected String path;
    @Schema(description = "备注")
    protected String note;
    @Schema(description = "下载链接")
    protected String link;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
