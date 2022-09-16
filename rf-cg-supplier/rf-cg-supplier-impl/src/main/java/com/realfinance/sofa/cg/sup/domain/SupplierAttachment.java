package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 供应商附件
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_ATTACHMENT")
public class SupplierAttachment implements IEntity<Integer> {

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

    @ManyToOne
    @JoinColumn(name = "supplier_id", updatable = false)
    private Supplier supplier;

    public Integer getId() {
        return id;
    }

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
