package com.realfinance.sofa.flow.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TaskTransitionInfoDto {

    /**
     * 任务ID
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务创建
     */
    private LocalDateTime createTime;

    /**
     * 任务领取时间
     */
    private LocalDateTime claimTime;

    /**
     * 任务处理人
     */
    private Integer assignee;

    /**
     * 流转集合
     */
    private List<Transition> transitions;

    /**
     * 评论信息
     */
    private List<CommentDto> comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(LocalDateTime claimTime) {
        this.claimTime = claimTime;
    }

    public Integer getAssignee() {
        return assignee;
    }

    public void setAssignee(Integer assignee) {
        this.assignee = assignee;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "TaskTransitionInfoDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", claimTime=" + claimTime +
                ", assignee=" + assignee +
                ", transitions=" + transitions +
                ", comments=" + comments +
                '}';
    }

    public static class Transition {
        private String nextNode;
        private String name;

        public String getNextNode() {
            return nextNode;
        }

        public void setNextNode(String nextNode) {
            this.nextNode = nextNode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Transition that = (Transition) o;
            return Objects.equals(nextNode, that.nextNode) &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nextNode, name);
        }

        @Override
        public String toString() {
            return "Transition{" +
                    "nextNode='" + nextNode + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
