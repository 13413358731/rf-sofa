package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 信用信息表
 */
@MappedSuperclass
public class Credit extends BaseEntity implements IEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //决定文书号
    @Column
    private String documentNumbers;
    //处罚项名称
    @Column
    private String penaltyItemNames;
    //处罚原因
    @Column
    private String penaltyCauses;
    //处罚日期
    @Column
    private String penaltyDates;
    //处罚类型(严重违法,海关失信,政府采购违法,电子商务失信,涉金融黑名单,环保违规处罚,税务行政处罚,假冒专利行为,工商行政处罚,工商行政处罚,安全生产黑名单,进出口行政处罚,环保行政,重大税收违法)
    @Column
    private String penaltyType;


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentNumbers() {
        return documentNumbers;
    }

    public void setDocumentNumbers(String documentNumbers) {
        this.documentNumbers = documentNumbers;
    }

    public String getPenaltyItemNames() {
        return penaltyItemNames;
    }

    public void setPenaltyItemNames(String penaltyItemNames) {
        this.penaltyItemNames = penaltyItemNames;
    }

    public String getPenaltyCauses() {
        return penaltyCauses;
    }

    public void setPenaltyCauses(String penaltyCauses) {
        this.penaltyCauses = penaltyCauses;
    }

    public String getPenaltyDates() {
        return penaltyDates;
    }

    public void setPenaltyDates(String penaltyDates) {
        this.penaltyDates = penaltyDates;
    }

    public String getPenaltyType() {
        return penaltyType;
    }

    public void setPenaltyType(String penaltyType) {
        this.penaltyType = penaltyType;
    }

}
