package com.realfinance.sofa.cg.core.domain;

/**
 * 流程状态
 */
public enum FlowStatus {
    /**
     * 未提交(编辑)
     */
    EDIT,
    /**
     * 已提交
     */
    SUBMITTED,
    /**
     * 通过
     */
    PASS,
    /**
     * 不通过
     */
    NO_PASS
}
