package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "附件对象")
public class CgAttVo extends BaseVo implements IdentityObject<Integer> {

    private Integer id;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    private String name;

    /**
     * 来源
     */
    @Schema(description = "来源")
    private String source;

    /**
     * 文件大小
     */
    @Schema(description = "大小")
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
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 上传人
     */
    protected UserVo uploader;

    /**
     * 是否已加密
     */
    @Schema(description = "是否已加密")
    private Boolean encrypted;

    @Schema(description = "附件标记")
    private String attSign;

    @Schema(description = "供应商")
    private CgSupplierVo supplier ;

    private String link;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "附件分类")
    protected String category;

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

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public UserVo getUploader() {
        return uploader;
    }

    public void setUploader(UserVo uploader) {
        this.uploader = uploader;
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

    public CgSupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierVo supplier) {
        this.supplier = supplier;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
