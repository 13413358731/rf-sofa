package com.realfinance.sofa.cg.core.domain.exec.bid;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseBiddingDocumentSection implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 段落名称
     */
    @Column(nullable = false)
    protected String sectionName;

    /**
     * 文本内容
     */
    @Column
    protected String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
