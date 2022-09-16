package com.realfinance.sofa.flow.model;

import javax.validation.constraints.NotNull;

public class TaskCompleteDto {
    public static final String COMPLETE_TYPE_COMPLETE = "COMPLETE"; // 完成
    public static final String COMPLETE_TYPE_BACK = "BACK"; // 回退
    public static final String COMPLETE_TYPE_STOP = "STOP"; // 终止
    public static final String COMPLETE_TYPE_MOVE = "MOVE"; // 自由跳转

    @NotNull
    private String id;
    private String completeType;
    private String comment;
    private String nextNode;
    private NextUserTaskInfo nextUserTaskInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompleteType() {
        return completeType;
    }

    public void setCompleteType(String completeType) {
        this.completeType = completeType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public NextUserTaskInfo getNextUserTaskInfo() {
        return nextUserTaskInfo;
    }

    public void setNextUserTaskInfo(NextUserTaskInfo nextUserTaskInfo) {
        this.nextUserTaskInfo = nextUserTaskInfo;
    }
}
