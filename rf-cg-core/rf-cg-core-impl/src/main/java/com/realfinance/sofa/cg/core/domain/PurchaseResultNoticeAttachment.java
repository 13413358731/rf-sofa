package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 采购结果通知附件
 */
@Entity
@Table(name = "CG_CORE_PURCHASE_RESULT_NOTICE_ATTACHMENT")
public class PurchaseResultNoticeAttachment extends BasePurAtt/*BaseEntity implements IEntity<Integer>*/ {

    /**
     * 主键
     */
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;*/

    /**
     * 附件名称
     */
   /* @Column
    private String name;*/

    /**
     * 上传时间
     */
   /* @Column
    private LocalDateTime uploadTime;
*/
    /**
     * 文件大小
     */
  /*  @Column
    private Long size;
*/
    /**
     * 扩展名
     */
  /*  @Column
    private String ext;
*/
    /**
     * 上传路径
     */
   /* @Column
    private String path;*/

    /**
     * 备注
     */
   /* @Column
    private String note;
*/

    /**
     * 来源
     */
   /* @Column
    private String source;
*/
  /*  @Column
    private String category;*/

    /**
     * 所属结果通知
     */
    @ManyToOne
    @JoinColumn(name = "resultnotice_id", referencedColumnName = "id")
    private PurchaseResultNotice purchaseResultNotice;

    /*@Override
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }*/

    public PurchaseResultNotice getPurchaseResultNotice() {
        return purchaseResultNotice;
    }

    public void setPurchaseResultNotice(PurchaseResultNotice purchaseResultNotice) {
        this.purchaseResultNotice = purchaseResultNotice;
    }
   /* public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }*/
}
