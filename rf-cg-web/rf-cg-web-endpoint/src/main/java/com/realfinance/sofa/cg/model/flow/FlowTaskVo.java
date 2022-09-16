package com.realfinance.sofa.cg.model.flow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.flow.model.NextUserTaskInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "流程提交对象")
public class FlowTaskVo extends TaskVo implements Serializable {

    public interface Complete { }
    public interface Resolve { }
    public interface Back { }
    public interface Delegate { }
    public interface Turn { }

    @Schema(description = "表单属性")
    private Object formProperties;
    @Schema(description = "意见、原因")
    private String comment;
    @Schema(description = "节点ID")
    @NotNull(groups = {Back.class})
    private String activitiId;
    @Schema(description = "目标用户ID")
    @NotNull(groups = {Delegate.class, Turn.class})
    private String targetUserId;
    @Schema(description = "下一步任务信息")
    private NextUserTaskInfo nextUserTaskInfo;
    @Schema(description = "表单数据")
    private Map<String, String> formData;

    @Override
    @NotNull(groups = {Complete.class, Resolve.class, Back.class, Delegate.class, Turn.class})
    public String getId() {
        return super.getId();
    }

    public Object getFormProperties() {
        return formProperties;
    }

    public void setFormProperties(Object formProperties) {
        this.formProperties = formProperties;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getActivitiId() {
        return activitiId;
    }

    public void setActivitiId(String activitiId) {
        this.activitiId = activitiId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public NextUserTaskInfo getNextUserTaskInfo() {
        return nextUserTaskInfo;
    }

    public void setNextUserTaskInfo(NextUserTaskInfo nextUserTaskInfo) {
        this.nextUserTaskInfo = nextUserTaskInfo;
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }
}
