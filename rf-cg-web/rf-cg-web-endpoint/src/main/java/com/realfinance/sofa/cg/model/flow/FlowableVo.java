package com.realfinance.sofa.cg.model.flow;

import com.realfinance.sofa.cg.model.EditableVo;
import com.realfinance.sofa.cg.model.system.UserVo;

import java.util.Objects;

import static com.realfinance.sofa.cg.util.SecurityUtils.getPrincipalId;

/**
 * 审批流数据对象Vo
 *
 */
public interface FlowableVo extends EditableVo {

    String EDIT_STATUS = "EDIT";
    String SUBMITTED_STATUS = "SUBMITTED";
    String PASS_STATUS = "PASS";
    String NO_PASS_STATUS = "NO_PASS";

    /**
     * 流程信息
     * @return
     */
    FlowInfoVo getFlowInfo();

    /**
     * 流程状态
     * @return
     */
    String getStatus();

    /**
     * 是否可编辑
     * 流程状态为EDIT 或者 任务节点配置为业务数据可编辑
     * @return
     */
    @Override
    default Boolean getCanEdit() {
        FlowInfoVo flowInfo = getFlowInfo();
        String status = getStatus();
        Integer principalId = getPrincipalId();
        if (flowInfo == null || status == null || principalId == null) {
            return null;
        }
        ProcessInstanceVo flowProcessInstance = flowInfo.getFlowProcessInstance();
        if (flowProcessInstance == null) {
            return EDIT_STATUS.equals(status);
        } else {
            FlowTaskVo flowTask = flowInfo.getFlowTask();
            if (flowTask == null) {
                return false;
            } else {
                Boolean businessDataEditable = flowTask.getBusinessDataEditable();
                if (businessDataEditable == null) {
                    return false;
                } else {
                    return businessDataEditable;
                }
            }
        }
    }

    default Boolean setCanEdit(){
        return true;
    }

    /**
     * 是否可启动流程
     * @return
     */
    default Boolean getCanStartProcess() {
        FlowInfoVo flowInfo = getFlowInfo();
        String status = getStatus();
        Integer principalId = getPrincipalId();
        if (flowInfo == null || status == null || principalId == null) {
            return null;
        }
        if (EDIT_STATUS.equals(getStatus())) {
            return true;
        }
        ProcessInstanceVo flowProcessInstance = flowInfo.getFlowProcessInstance();
        if (SUBMITTED_STATUS.equals(status) && flowProcessInstance == null) {
            return true;
        }
        return false;
    }

    /**
     * 是否可终止流程
     * @return
     */
    default Boolean getCanDeleteProcess() {
        FlowInfoVo flowInfo = getFlowInfo();
        String status = getStatus();
        Integer principalId = getPrincipalId();
        if (flowInfo == null || status == null || principalId == null) {
            return null;
        }
        ProcessInstanceVo flowProcessInstance = flowInfo.getFlowProcessInstance();
        if (flowProcessInstance == null) {
            return false;
        }
        UserVo startUser = flowProcessInstance.getStartUser();
        if (startUser == null) {
            return false;
        }
        return Objects.equals(startUser.getId(), principalId);
    }

    /**
     * 是否能完成任务
     * @return
     */
    default Boolean getCanCompleteTask() {
        FlowInfoVo flowInfo = getFlowInfo();
        String status = getStatus();
        Integer principalId = getPrincipalId();
        if (flowInfo == null || status == null || principalId == null) {
            return null;
        }
        ProcessInstanceVo flowProcessInstance = flowInfo.getFlowProcessInstance();
        if (flowProcessInstance == null) {
            return false;
        }
        FlowTaskVo flowTask = flowInfo.getFlowTask();
        if (flowTask == null) {
            return false;
        }
        UserVo assignee = flowTask.getAssignee();
        if (assignee == null) {
            return false;
        }
        String delegateStatus = flowTask.getDelegateStatus();
        return !"PENDING".equals(delegateStatus) && Objects.equals(assignee.getId(),principalId);
    }

    /**
     * 是否能完成委派
     * @return
     */
    default Boolean getCanResolveTask() {
        FlowInfoVo flowInfo = getFlowInfo();
        String status = getStatus();
        Integer principalId = getPrincipalId();
        if (flowInfo == null || status == null || principalId == null) {
            return null;
        }
        ProcessInstanceVo flowProcessInstance = flowInfo.getFlowProcessInstance();
        if (flowProcessInstance == null) {
            return false;
        }
        FlowTaskVo flowTask = flowInfo.getFlowTask();
        if (flowTask == null) {
            return false;
        }
        UserVo assignee = flowTask.getAssignee();
        if (assignee == null) {
            return false;
        }
        String delegateStatus = flowTask.getDelegateStatus();
        return "PENDING".equals(delegateStatus) && Objects.equals(assignee.getId(),principalId);
    }

    /**
     * 是否能回退任务
     * @return
     */
    default Boolean getCanBackTask() {
        return getCanCompleteTask();
    }

    /**
     * 能否委派任务
     * @return
     */
    default Boolean getCanDelegateTask() {
        return getCanCompleteTask();
    }

    /**
     * 能否转办任务
     * @return
     */
    default Boolean getCanTurnTask() {
        Boolean canCompleteTask = getCanCompleteTask();
        Boolean canResolveTask = getCanResolveTask();
        if (canCompleteTask == null && canResolveTask == null) {
            return null;
        }
        return (canCompleteTask != null && canCompleteTask) || (canResolveTask != null && canResolveTask);
    }

    /**
     * 能否查询流程流转以历史
     * @return
     */
    default Boolean getCanViewProcessHistory() {
        FlowInfoVo flowInfo = getFlowInfo();
        String status = getStatus();
        Integer principalId = getPrincipalId();
        if (flowInfo == null || status == null || principalId == null) {
            return null;
        }
        return !EDIT_STATUS.equals(getStatus()) || flowInfo.getFlowProcessInstance() != null;
    }
}
