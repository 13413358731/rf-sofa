package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BasePurOaDatum implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 立项审批号
     */
    @Column
    protected String approvalNo;

    /**
     * 审批文件提名
     */
    protected String approvalTitle;

    /**
     * 立项金额
     */
    protected BigDecimal approvalAmount;

    /**
     * 本次采购金额
     */
    protected BigDecimal thisPurAmount;

    /**
     * 已占用金额
     */
    @Column()
    protected BigDecimal usedAmount;

    /**
     * 可使用金额
     */
    @Column()
    protected BigDecimal remainAmount;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    public String getApprovalTitle() {
        return approvalTitle;
    }

    public void setApprovalTitle(String approvalTitle) {
        this.approvalTitle = approvalTitle;
    }

    public BigDecimal getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(BigDecimal approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public BigDecimal getThisPurAmount() {
        return thisPurAmount;
    }

    public void setThisPurAmount(BigDecimal thisPurAmount) {
        this.thisPurAmount = thisPurAmount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }
}
