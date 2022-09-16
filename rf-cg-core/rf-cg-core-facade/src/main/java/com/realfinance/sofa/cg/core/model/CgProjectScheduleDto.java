package com.realfinance.sofa.cg.core.model;

import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

/**
 * @author hhq
 * @date 2021/6/21 - 19:13
 */
public class CgProjectScheduleDto extends BaseDto  {
    private Integer id;

    private String tenantId;

    private Long v;

    private String projectName;

    private Integer projectStage;

    private Integer projectStatus;

    private Integer contractStatus;

    private LocalDateTime reqSubmitTime;

    private LocalDateTime reqPassTime;

    private Integer reqStandardDays;

    private Integer reqDays;

    private CgRequirementDto requirement;

    private Boolean attention;

//    private Integer projectScheduleUserId;
    private CgProjectScheduleUserDto projectScheduleUser;

    public Integer getId() {
        return id;
    }

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

    public CgRequirementDto getRequirement() {
        return requirement;
    }

    public void setRequirement(CgRequirementDto requirement) {
        this.requirement = requirement;
    }

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }

//    public Integer getProjectScheduleUserId() {
//        return projectScheduleUserId;
//    }
//
//    public void setProjectScheduleUserId(Integer projectScheduleUserId) {
//        this.projectScheduleUserId = projectScheduleUserId;
//    }


    public CgProjectScheduleUserDto getProjectScheduleUser() {
        return projectScheduleUser;
    }

    public void setProjectScheduleUser(CgProjectScheduleUserDto projectScheduleUser) {
        this.projectScheduleUser = projectScheduleUser;
    }
}
