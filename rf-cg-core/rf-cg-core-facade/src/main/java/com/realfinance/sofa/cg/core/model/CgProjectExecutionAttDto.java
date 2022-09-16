package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgProjectExecutionAttDto extends BaseDto {

    private Integer id;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 来源
     */
    private String source;

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
     * 环节类型
     */
    private String stepType;

    /**
     * 环节数据ID
     */
    private Integer stepDataId;

    /**
     * 是否已加密
     */
    private Boolean encrypted;

    private String attSign;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    private LocalDateTime uploadTime;

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

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public Integer getStepDataId() {
        return stepDataId;
    }

    public void setStepDataId(Integer stepDataId) {
        this.stepDataId = stepDataId;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getAttSign() {
        return attSign;
    }

    public void setAttSign(String attSign) {
        this.attSign = attSign;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
}
