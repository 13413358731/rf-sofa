package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "评标定标报告对象")
public class CgCalibrationVo implements IdentityObject<Integer> {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 选取的评标方法（拟用评审方式）
     */
    private String evalMethod;

    /**
     * 评标时间
     */
    private LocalDateTime biddingTime;

    /**
     *通知专家(评标地点)
     */
    private String noticeExpert;

    /**
     * 会议决议(评标定标结果)
     */
    private String resolutionContent;

    /**
     * 监督人
     */
    private UserVo supervisor;

    /**
     * 评审专家表决意见
     */
    private List<CgMeetingConfereeVo> expertResult;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getEvalMethod() {
        return evalMethod;
    }

    public void setEvalMethod(String evalMethod) {
        this.evalMethod = evalMethod;
    }

    public LocalDateTime getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(LocalDateTime biddingTime) {
        this.biddingTime = biddingTime;
    }

    public String getNoticeExpert() {
        return noticeExpert;
    }

    public void setNoticeExpert(String noticeExpert) {
        this.noticeExpert = noticeExpert;
    }

    public String getResolutionContent() {
        return resolutionContent;
    }

    public void setResolutionContent(String resolutionContent) {
        this.resolutionContent = resolutionContent;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer integer) {

    }

    public UserVo getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(UserVo supervisor) {
        this.supervisor = supervisor;
    }

    public List<CgMeetingConfereeVo> getExpertResult() {
        return expertResult;
    }

    public void setExpertResult(List<CgMeetingConfereeVo> expertResult) {
        this.expertResult = expertResult;
    }
}
