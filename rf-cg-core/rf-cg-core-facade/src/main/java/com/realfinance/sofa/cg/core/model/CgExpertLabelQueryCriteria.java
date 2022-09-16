package com.realfinance.sofa.cg.core.model;

public class CgExpertLabelQueryCriteria {

    private Boolean parentIsNull;

    private Integer parentId;

    /**
     * id查询
     */
    private String id;

    /**
     * 标签名称模糊
     */
    private String nameLike;

    private Integer expertLabelTypeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Boolean getParentIsNull() {
        return parentIsNull;
    }

    public void setParentIsNull(Boolean parentIsNull) {
        this.parentIsNull = parentIsNull;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getExpertLabelTypeId() {
        return expertLabelTypeId;
    }

    public void setExpertLabelTypeId(Integer expertLabelTypeId) {
        this.expertLabelTypeId = expertLabelTypeId;
    }

}
