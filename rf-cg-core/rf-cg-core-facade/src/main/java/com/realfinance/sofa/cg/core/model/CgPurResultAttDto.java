package com.realfinance.sofa.cg.core.model;

public class CgPurResultAttDto extends BaseDto{

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

    private String uploadTime;

    /**
     * 文件路径
     */
    private String path;

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

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
