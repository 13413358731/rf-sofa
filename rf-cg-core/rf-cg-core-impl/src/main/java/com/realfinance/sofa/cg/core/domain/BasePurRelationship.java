package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasePurRelationship extends BaseEntity implements IEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 关联序号(组号)
     */
    protected Integer number;

    //关联类型(1-5条件)
    protected Integer type;

    //实际控股
    protected Double proportion;

    /**
     * 公司名称(企业名称)
     */
    protected String name;

    /**
     * 统一社会信用代码
     */
    protected String unifiedSocialCreditCode;

    /**
     * 法定代表人
     */
    protected String statutoryRepresentative;

    /**
     *股东名称
     */
    protected String shareholderNames;

    /**
     *股东类型
     */
    protected String shareholderTypes;

    /**
     * 出资比例(股权占比)
     */
    protected String equityRatio;


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    public String getStatutoryRepresentative() {
        return statutoryRepresentative;
    }

    public void setStatutoryRepresentative(String statutoryRepresentative) {
        this.statutoryRepresentative = statutoryRepresentative;
    }

    public String getShareholderNames() {
        return shareholderNames;
    }

    public void setShareholderNames(String shareholderNames) {
        this.shareholderNames = shareholderNames;
    }

    public String getShareholderTypes() {
        return shareholderTypes;
    }

    public void setShareholderTypes(String shareholderTypes) {
        this.shareholderTypes = shareholderTypes;
    }

    public String getEquityRatio() {
        return equityRatio;
    }

    public void setEquityRatio(String equityRatio) {
        this.equityRatio = equityRatio;
    }
}
