package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 附件
 */
@MappedSuperclass
public class Attachment extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 附件分类
     */
    @Column
    @Enumerated
    private SupplierAttachmentCategory category;

    /**
     * 附件名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 上传时间
     */
    @Column(nullable = false)
    private LocalDateTime uploadTime;

    /**
     * 文件大小
     */
    @Column(nullable = false)
    private Long size;

    /**
     * 扩展名
     */
    @Column
    private String ext;

    /**
     * 文件路径
     */
    @Column
    private String path;

    /**
     * 备注
     */
    @Column
    private String note;

    /**
     * 来源
     */
    @Column
    private String source;

    /**
     * 上传人
     */
    @Column
    protected Integer uploader;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public SupplierAttachmentCategory getCategory() {
        return category;
    }

    public void setCategory(SupplierAttachmentCategory category) {
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
}
