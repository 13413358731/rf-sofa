package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDateTime;

public class CgSupplierAttachmentDto {

    /**
     * 附件分类
     */
    private String category;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 扩展名
     */
    private String ext;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 备注
     */
    private String note;

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
}
