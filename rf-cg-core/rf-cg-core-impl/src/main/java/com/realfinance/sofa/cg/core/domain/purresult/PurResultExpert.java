package com.realfinance.sofa.cg.core.domain.purresult;

import com.realfinance.sofa.cg.core.domain.DrawExpertWay;

import javax.persistence.*;

@Entity
@Table(name = "CG_CORE_PUR_RESULT_EXPERT")
public class PurResultExpert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 专家对象
     */
//    @Column(nullable = false)
//    private Integer expert;

    /**
     * 姓名
     */
    @Column(nullable = false)
    private String name;

    /**
     * 部门
     */
    @Column(nullable = false)
    private Integer expertDepartment;

    /**
     * 备注
     */
    @Column()
    private String note;

    /**
     * 抽取方式
     */
    private DrawExpertWay drawWay;

    @ManyToOne
    @JoinColumn(name = "pur_result_id", updatable = false)
    private PurchaseResult purchaseResult;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpertDepartment() {
        return expertDepartment;
    }

    public void setExpertDepartment(Integer expertDepartment) {
        this.expertDepartment = expertDepartment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PurchaseResult getPurchaseResult() {
        return purchaseResult;
    }

    public void setPurchaseResult(PurchaseResult purchaseResult) {
        this.purchaseResult = purchaseResult;
    }

    public DrawExpertWay getDrawWay() {
        return drawWay;
    }

    public void setDrawWay(DrawExpertWay drawWay) {
        this.drawWay = drawWay;
    }
}
