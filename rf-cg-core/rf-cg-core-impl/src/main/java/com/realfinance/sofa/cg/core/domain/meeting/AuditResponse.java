package com.realfinance.sofa.cg.core.domain.meeting;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 响应性审查
 */
@Entity
@Table(name = "CG_CORE_AUDIT_RESPONSE")
public class AuditResponse extends BaseEntity implements IEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 大项编码
     */
    @Column()
    private String code;

    /**
     * 大项名称
     */
    @Column()
    private String name;

    /**
     * 细项编码
     */
    @Column()
    private String subCode;

    /**
     * 细项名称
     */
    @Column()
    private String subName;

    /**
     * 推荐供应商
     */
    @Column()
    private Integer supplier;

    /**
     * 评审会专家
     */
    @Column()
    private Integer expert;

    /**
     * 是否通过
     */
    @Column
    private Boolean pass;

    /**
     * 标书Id
     */
    @Column
    private Integer biddingDocumentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Integer getExpert() {
        return expert;
    }

    public void setExpert(Integer expert) {
        this.expert = expert;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Integer getBiddingDocumentId() {
        return biddingDocumentId;
    }

    public void setBiddingDocumentId(Integer biddingDocumentId) {
        this.biddingDocumentId = biddingDocumentId;
    }
}
