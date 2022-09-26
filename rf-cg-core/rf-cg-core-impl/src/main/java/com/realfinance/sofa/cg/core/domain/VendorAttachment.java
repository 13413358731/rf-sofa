package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 供应商评价附件
 */
@Entity
@Table(name = "CG_CORE_VENDOR_ATTACHMENT")
public class VendorAttachment implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 附件人
     */
    @Column
    private Integer uploader;

    @ManyToOne
    @JoinColumn(name = "vendor_rating_id", updatable = false)
    private VendorRating vendorRating;

    public Integer getId() {
        return id;
    }

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

    public VendorRating getVendorRating() {
        return vendorRating;
    }

    public void setVendorRating(VendorRating vendorRating) {
        this.vendorRating = vendorRating;
    }
}
