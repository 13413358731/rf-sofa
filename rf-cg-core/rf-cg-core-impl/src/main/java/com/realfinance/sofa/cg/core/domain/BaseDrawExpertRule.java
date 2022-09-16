package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;

/**
 * 专家抽取规则
 */
@MappedSuperclass
public abstract class BaseDrawExpertRule implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    protected Integer expertDeptId;

    @Column(nullable = false)
    protected Integer expertLabelId;

    @Column(nullable = false)
    protected Integer expertCount;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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
