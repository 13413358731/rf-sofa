package com.realfinance.sofa.flow.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

public class NextUserTaskInfo implements Serializable {

    private Collection<String> assignees;
    private LocalDateTime dueDate;
    private Integer priority;

    public Collection<String> getAssignees() {
        return assignees;
    }

    public void setAssignees(Collection<String> assignees) {
        this.assignees = assignees;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "NextUserTaskInfo{" +
                "assignees=" + assignees +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                '}';
    }
}
