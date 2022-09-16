package com.realfinance.sofa.cg.controller.flow;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.flow.TaskVo;
import com.realfinance.sofa.cg.service.mapstruct.FlowMapper;
import com.realfinance.sofa.flow.facade.TaskFacade;
import com.realfinance.sofa.flow.model.HistoricTaskInstanceQueryCriteria;
import com.realfinance.sofa.flow.model.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Tag(name = "已办任务")
@RequestMapping("/mytask/historytask")
public class MyHistoryTaskController {

    @SofaReference(interfaceType = TaskFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private TaskFacade taskFacade;

    @Resource
    private FlowMapper flowMapper;

    @GetMapping("list")
    @Operation(summary = "查询已办任务")
    @Parameters({
            @Parameter(name = "processDefinitionNameLike", schema = @Schema(type = "string"), description = "流程名称模糊", in = ParameterIn.QUERY),
            @Parameter(name = "taskNameLike", schema = @Schema(type = "string"), description = "环节名称", in = ParameterIn.QUERY),
            @Parameter(name = "taskPriority", schema = @Schema(type = "string"), description = "优先级", in = ParameterIn.QUERY),
            @Parameter(name = "taskCreatedBefore", schema = @Schema(type = "string", format = "date-time"), description = "返回在此时间之前创建的", in = ParameterIn.QUERY),
            @Parameter(name = "taskCreatedAfter", schema = @Schema(type = "string", format = "date-time"), description = "返回在此时间之后创建的", in = ParameterIn.QUERY),
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<TaskVo>> list(HistoricTaskInstanceQueryCriteria queryCriteria,
                                             Pageable pageable,
                                             Authentication authentication) {
        queryCriteria.setTaskAssignee(authentication.getName());
        Page<TaskDto> result = taskFacade.listHistory(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(flowMapper::taskDto2TaskVo));
    }

    @GetMapping("getBizUrl")
    @Operation(summary = "查询业务跳转URL")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getBizUrl(String id) {
        String bizUrl = taskFacade.getBizUrl(id);
        return ResponseEntity.ok(bizUrl);
    }
}
