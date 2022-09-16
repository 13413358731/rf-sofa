package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 附件
 */
//@Entity
//@Table(name = "CG_CORE_ATTACHMENT")
//@Inheritance(strategy=InheritanceType.JOINED)
@MappedSuperclass
public class Attachment extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 附件名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 来源
     */
    @Column
    private String source;

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
     * 上传时间
     */
    @Column(nullable = false)
    protected LocalDateTime uploadTime;

    /**
     * 上传人
     */
    @Column()
    protected Integer uploader;

    /**
     * 文件路径
     */
    @Column
    private String path;

    @Column
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
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

    public Integer getUploader() {
        return uploader;
    }

    public void setUploader(Integer uploader) {
        this.uploader = uploader;
    }
}
