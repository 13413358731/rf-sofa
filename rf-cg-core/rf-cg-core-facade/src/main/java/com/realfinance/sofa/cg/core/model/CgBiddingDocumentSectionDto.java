package com.realfinance.sofa.cg.core.model;

public class CgBiddingDocumentSectionDto {
    protected Integer id;

    /**
     * 段落名称
     */
    protected String sectionName;

    /**
     * 文本内容
     */
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
