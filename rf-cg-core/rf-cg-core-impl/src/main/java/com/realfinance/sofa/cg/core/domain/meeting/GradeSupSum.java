package com.realfinance.sofa.cg.core.domain.meeting;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStep;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 汇总专家打分
 */
@Entity
@Table(name = "CG_CORE_GRADE_SUP_SUM")
public class GradeSupSum extends BaseEntity implements IEntity<Integer> {

    /**
     * 供应商名称
     */
    @Column()
    private Integer supplier;

    /**
     * 综合得分
     */
    @Column(precision = 10,scale = 2)
    private Double sumScore;

    /**
     * 名次
     */
    @Column()
    private Integer ranking;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 方案Id
     */
    @Column
    private Integer projId;

    /**
     * 会议Id
     */
    @Column
    private Integer meetingId;

    /**
     * 是否汇总
     */
    @Column
    private Boolean IsSum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "grade_sup_sum_id")
    private List<GradeSup> gradeSups;

    public GradeSupSum() {
        gradeSups = new ArrayList<>();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Double getSumScore() {
        return sumScore;
    }

    public void setSumScore(Double sumScore) {
        this.sumScore = sumScore;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Boolean getSum() {
        return IsSum;
    }

    public void setSum(Boolean sum) {
        IsSum = sum;
    }

    public List<GradeSup> getGradeSups() {
        return gradeSups;
    }

    public void setGradeSups(List<GradeSup> gradeSups) {
        this.gradeSups = gradeSups;
    }
}
