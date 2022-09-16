package com.realfinance.sofa.cg.core.domain.exec.bid;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 供应商审查
 */
@MappedSuperclass
public abstract class BaseBiddingDocumentExamination implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 大项编码
     */
    @Column(nullable = false)
    protected String code;

    /**
     * 大项名称
     */
    @Column(nullable = false)
    protected String name;

    /**
     * 细项编码
     */
    @Column(nullable = false)
    protected String subCode;

    /**
     * 细项名称
     */
    @Column(nullable = false)
    protected String subName;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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
}
