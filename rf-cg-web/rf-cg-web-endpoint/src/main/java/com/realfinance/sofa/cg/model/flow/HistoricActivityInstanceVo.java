package com.realfinance.sofa.cg.model.flow;

import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.flow.model.CommentDto;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class HistoricActivityInstanceVo implements Serializable {

    protected String activityName;
    protected String activityType;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected Long durationInMillis;
    protected UserVo assignee;
    protected String deleteReason;
    protected List<CommentDto> comments;

    public String getDurationDisplay() {
        if (durationInMillis == null) {
            return null;
        }
        Duration duration = Duration.ofMillis(durationInMillis);
        int count = 0;
        StringBuilder sb = new StringBuilder();
        int hoursPart = duration.toHoursPart();
        if (hoursPart != 0) {
            sb.append(hoursPart).append("小时");
            count ++;
        }
        int minutesPart = duration.toMinutesPart();
        if (minutesPart != 0) {
            sb.append(minutesPart).append("分");
            count ++;
        }
        int secondsPart = duration.toSecondsPart();
        if (count < 2 && secondsPart != 0) {
            sb.append(secondsPart).append("秒");
            count ++;
        }
        int millisPart = duration.toMillisPart();
        if (count < 2 && millisPart != 0) {
            sb.append(duration.toMillisPart()).append("毫秒");
        }
        return sb.toString();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(Long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public UserVo getAssignee() {
        return assignee;
    }

    public void setAssignee(UserVo assignee) {
        this.assignee = assignee;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
