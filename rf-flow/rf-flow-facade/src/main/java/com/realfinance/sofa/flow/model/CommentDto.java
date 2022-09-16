package com.realfinance.sofa.flow.model;

import java.util.Date;

public class CommentDto {
    protected String id;
    protected Date time;
    protected Integer userId;
    protected String taskId;
    protected String message;
    protected String fullMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", userId=" + userId +
                ", taskId='" + taskId + '\'' +
                ", message='" + message + '\'' +
                ", fullMessage='" + fullMessage + '\'' +
                '}';
    }
}
