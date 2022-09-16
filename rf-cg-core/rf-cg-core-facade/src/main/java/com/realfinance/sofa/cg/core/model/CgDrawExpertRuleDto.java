package com.realfinance.sofa.cg.core.model;

public class CgDrawExpertRuleDto extends BaseDto {

    private Integer id;

    /**
     *专家部门
     */
    private Integer expertDeptId;

    /**
     *专家标签
     */
    private Integer expertLabelId;

    /**
     *抽取人数
     */
    private Integer expertCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExpertDeptId() {
        return expertDeptId;
    }

    public void setExpertDeptId(Integer expertDeptId) {
        this.expertDeptId = expertDeptId;
    }

    public Integer getExpertLabelId() {
        return expertLabelId;
    }

    public void setExpertLabelId(Integer expertLabelId) {
        this.expertLabelId = expertLabelId;
    }

    public Integer getExpertCount() {
        return expertCount;
    }

    public void setExpertCount(Integer expertCount) {
        this.expertCount = expertCount;
    }
}
