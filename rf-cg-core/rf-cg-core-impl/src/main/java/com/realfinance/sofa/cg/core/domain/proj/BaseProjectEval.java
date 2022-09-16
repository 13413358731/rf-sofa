package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 评分大项
 */
@MappedSuperclass
public abstract class BaseProjectEval implements IEntity<Integer> {

    public enum Category {
        /**
         * 价格部分
         */
        JGBF,
        /**
         * 技术部分
         */
        JSBF,
        /**
         * 商务部分
         */
        SWBF,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 评分分类
     */
    @Enumerated
    @Column(nullable = false)
    protected Category category;

    /**
     * 权重
     */
    protected BigDecimal weight;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

}
