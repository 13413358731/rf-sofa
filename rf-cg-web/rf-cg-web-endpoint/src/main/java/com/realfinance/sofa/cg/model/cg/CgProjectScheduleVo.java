package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author hhq
 * @date 2021/6/21 - 19:30
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "项目进度表对象")
public class CgProjectScheduleVo extends BaseVo implements IdentityObject<Integer> {

    private Integer id;

    private String tenantId;

    /**
     * 乐观锁版本号
     */
    @Schema(name = "乐观锁版本号")
    private Long v;

    /**
     * 项目名称
     */
    @Schema(name = "项目名称")
    private String projectName;

    /**
     * 项目阶段
     */
    @Schema(name = "项目阶段")
    private Integer projectStage;

    /**
     * 项目状态
     */
    @Schema(name = "项目状态")
    private Integer projectStatus;

    /**
     * 合同状态
     */
    @Schema(name = "合同状态")
    private Integer contractStatus;

    /**
     * 采购申请提交时间
     */
    @Schema(name = "采购申请提交时间")
    private LocalDateTime reqSubmitTime;

    /**
     * 采购申请完成时间
     */
    @Schema(name = "采购申请完成时间")
    private LocalDateTime reqPassTime;

    /**
     * 采购申请标准用时
     */
    @Schema(name = "采购申请标准用时")
    private Integer reqStandardDays;

    /**
     * 采购申请实际用时
     */
    @Schema(name = "采购申请实际用时")
    private Integer reqDays;

    /**
     * 采购申请对象
     */
    @Schema(name = "采购申请对象")
    private CgRequirementVo requirement;

    @Schema(name = "是否关注")
    private Boolean attention;

    @Schema(name = "项目进度对象关联ID")
    private CgProjectScheduleUserVo projectScheduleUser;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(Integer projectStage) {
        this.projectStage = projectStage;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public LocalDateTime getReqSubmitTime() {
        return reqSubmitTime;
    }

    public void setReqSubmitTime(LocalDateTime reqSubmitTime) {
        this.reqSubmitTime = reqSubmitTime;
    }

    public LocalDateTime getReqPassTime() {
        return reqPassTime;
    }

    public void setReqPassTime(LocalDateTime reqPassTime) {
        this.reqPassTime = reqPassTime;
    }

    public Integer getReqStandardDays() {
        return reqStandardDays;
    }

    public void setReqStandardDays(Integer reqStandardDays) {
        this.reqStandardDays = reqStandardDays;
    }

    public Integer getReqDays() {
        return reqDays;
    }

    public void setReqDays(Integer reqDays) {
        this.reqDays = reqDays;
    }

    public CgRequirementVo getRequirement() {
        return requirement;
    }

    public void setRequirement(CgRequirementVo requirement) {
        this.requirement = requirement;
    }

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }


    public CgProjectScheduleUserVo getProjectScheduleUser() {
        return projectScheduleUser;
    }

    public void setProjectScheduleUser(CgProjectScheduleUserVo projectScheduleUser) {
        this.projectScheduleUser = projectScheduleUser;
    }
}
