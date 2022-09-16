package com.realfinance.sofa.cg.controller.flow;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinance.sofa.cg.model.flow.PortalTaskDataVo;
import com.realfinance.sofa.cg.model.flow.PortalTaskVo;
import com.realfinance.sofa.cg.model.flow.TaskVo;
import com.realfinance.sofa.cg.service.mapstruct.FlowMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.facade.TaskFacade;
import com.realfinance.sofa.flow.model.TaskDto;
import com.realfinance.sofa.flow.model.TaskQueryCriteria;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Tag(name = "待办任务")
@RequestMapping("/mytask/todotask")
public class MyTodoTaskController {

    private static final Logger log = LoggerFactory.getLogger(MyTodoTaskController.class);


    public static final String MENU_CODE_ROOT = "todotask";

    @SofaReference(interfaceType = TaskFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private TaskFacade taskFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private FlowMapper flowMapper;

    private final static Map<String, PortalTaskVo> map = new HashMap<>();

    public static void putMap(String id, PortalTaskVo portalTaskVo) {
        map.put(id, portalTaskVo);
    }

    @GetMapping("list")
    @Operation(summary = "查询待办任务")
    @Parameters({
            @Parameter(name = "processDefinitionNameLike", schema = @Schema(type = "string"), description = "流程名称模糊", in = ParameterIn.QUERY),
            @Parameter(name = "nameLike", schema = @Schema(type = "string"), description = "环节名称", in = ParameterIn.QUERY),
            @Parameter(name = "priority", schema = @Schema(type = "string"), description = "优先级", in = ParameterIn.QUERY),
            @Parameter(name = "createdBefore", schema = @Schema(type = "string", format = "date-time"), description = "创建时间之前", in = ParameterIn.QUERY),
            @Parameter(name = "createdAfter", schema = @Schema(type = "string", format = "date-time"), description = "创建时间之后", in = ParameterIn.QUERY),
            @Parameter(name = "dueBefore", schema = @Schema(type = "string", format = "date-time"), description = "期限之前", in = ParameterIn.QUERY),
            @Parameter(name = "dueAfter", schema = @Schema(type = "string", format = "date-time"), description = "期限之后", in = ParameterIn.QUERY),
            @Parameter(name = "dueAfter", schema = @Schema(type = "string", format = "date-time"), description = "期限之后", in = ParameterIn.QUERY),
            @Parameter(name = "referenceIds", schema = @Schema(type = "string"), description = "业务编码Id", in = ParameterIn.QUERY),
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<TaskVo>> list(@Parameter(hidden = true) TaskQueryCriteria queryCriteria,
                                             Pageable pageable,
                                             Authentication authentication) {
        //筛选直接指派,包括候选人,候选组. 候选组不生效,因为关闭了idm flow自带的身份权限管理,因为用户表需要改
        queryCriteria.setCandidateOrAssigned(authentication.getName());
        //获取登录人机构code
        String departmentCode = "D_" + DataScopeUtils.loadDepartmentCode().get();
        //获取登录人所有角色code
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> groupIn = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(e -> e.startsWith("ROLE_"))
                .map(e -> "R_" + e.substring("ROLE_".length()))
                .collect(Collectors.toList());
        groupIn.add(departmentCode);
        queryCriteria.setCandidateGroupIn(groupIn);
        Page<TaskDto> result = taskFacade.list(queryCriteria, pageable);
        Page<TaskVo> map = result.map(flowMapper::taskDto2TaskVo);
        
        return ResponseEntity.ok(map);
    }

    @GetMapping("getBizUrl")
    @Operation(summary = "查询业务跳转URL")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getBizUrl(@Parameter(description = "任务ID") @RequestParam String id) {
        String bizUrl = taskFacade.getBizUrl(id);
        return ResponseEntity.ok(bizUrl);
    }

    @PostMapping("claim")
    @Operation(summary = "签收任务")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> claim(@Parameter(description = "任务ID") @RequestParam String id) {
        taskFacade.claim(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("completesystemtask")
    @Operation(summary = "完成系统任务")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> completeSystemTask(@Parameter(description = "任务ID") @RequestParam String id,
                                                @Parameter(description = "流程实例referenceId") @RequestParam String businessCode) {
        if (businessCode.equals("internalresultnotice") || businessCode.equals("contract") || businessCode.equals("evaluateReminder")) {

            taskFacade.complete(businessCode, id);
            return ResponseEntity.ok().build();
        }
        throw new RuntimeException("非系统任务");
    }

    @GetMapping("portalTaskVo")
    @Operation(summary = "统一待办")
    public Object portalTaskVo(@Parameter(description = "获取待办的详细信息") @RequestParam(value = "DATA_TYPE", required = false) String dataType,
                               @Parameter(description = "查询待办的记录数") @RequestParam(value = "SIZE", required = false) String size,
                               @RequestParam(value = "task", required = false) String task,
                               Authentication authentication,
                               HttpServletResponse response) {
        if (task != null) {
            PortalTaskVo portalTaskVo = new PortalTaskVo();
            portalTaskVo.setTotal(task);
            portalTaskVo.setMoreUrl("http://172.16.66.208:9527/#/tytask");
            portalTaskVo.setOpenType("1");
            portalTaskVo.setPlatCode("SRCPS");
            List<PortalTaskDataVo> list = new ArrayList<>();
            PortalTaskDataVo portalTaskDataVo = new PortalTaskDataVo();
            portalTaskDataVo.setAppCode("SRCPS");
            portalTaskDataVo.setTitle("待处理待办共" + task + "条");
            portalTaskDataVo.setUrl("http://172.16.66.208:9527/#/tytask");
            portalTaskDataVo.setUrgentLev("99");
            list.add(portalTaskDataVo);
            portalTaskVo.setData(list);
            ObjectMapper om = new ObjectMapper();
            String returnJson = null;
            try {
                returnJson = om.writeValueAsString(portalTaskVo);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            System.out.println(returnJson);
            StringBuffer data = new StringBuffer("workItem_SRCPS" + "(");
            data.append(returnJson);
            data.append(")");
//            response.setHeader("Content-type", "text/plain");
            /*System.out.println(data.toString());
            try {
                response.getWriter().write(data.toString());
            } catch (IOException e) {
                log.error("待办数据获取异常" + e);
            }
            System.out.println(data);*/
            return returnJson;
        }
        if (authentication == null) {
            try {
//                response.sendRedirect("http://iam.eip.sdebank.com:8084/idp/oauth2/authorize?state=348&client_id=SRCPS&response_type=code&redirect_uri=http%3A%2F%2F107.255.18.100%3A8888%2F%23%2Ftytask");
                response.sendRedirect("http://iam.eip.sdebank.com:8084/idp/oauth2/authorize?state=348&client_id=SRCPS&response_type=code&redirect_uri=http%3A%2F%2F172.16.66.207%3A8888%2FssoLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Pageable pageable = PageRequest.of(0, Integer.parseInt(size));
            TaskQueryCriteria queryCriteria = new TaskQueryCriteria();
            //获取登录人机构code
            String departmentCode = "D_" + systemQueryFacade.findDepartmentCodeToUserId(Integer.parseInt(authentication.getName()));
            //筛选直接指派,包括候选人,候选组. 候选组不生效,因为关闭了idm flow自带的身份权限管理,因为用户表需要改
            queryCriteria.setCandidateOrAssigned(authentication.getName());
            //获取登录人所有角色code
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> groupIn = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(e -> e.startsWith("ROLE_"))
                    .map(e -> "R_" + e.substring("ROLE_".length()))
                    .collect(Collectors.toList());
            groupIn.add(departmentCode);
            queryCriteria.setCandidateGroupIn(groupIn);
            Page<TaskDto> result = taskFacade.list(queryCriteria, pageable);
            PortalTaskVo portalTaskVo = new PortalTaskVo();
            portalTaskVo.setTotal(Long.toString(result.getTotalElements()));
            portalTaskVo.setMoreUrl("http://172.16.66.208:9527/#/tytask");
            portalTaskVo.setOpenType("1");
            portalTaskVo.setPlatCode("SRCPS");
            List<PortalTaskDataVo> list = new ArrayList<>();
            PortalTaskDataVo portalTaskDataVo = new PortalTaskDataVo();
            portalTaskDataVo.setAppCode("SRCPS");
            portalTaskDataVo.setTitle("待处理待办共" + result.getTotalElements() + "条");
            portalTaskDataVo.setUrgentLev("99");
            list.add(portalTaskDataVo);
            portalTaskVo.setData(list);
            ObjectMapper om = new ObjectMapper();
            String returnJson = null;
            try {
                returnJson = om.writeValueAsString(portalTaskVo);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            System.out.println(returnJson);
            StringBuffer data = new StringBuffer("workItem_SRCPS" + "(");
            data.append(returnJson);
            data.append(")");
            /*PortalTaskVo portalTaskVo = new PortalTaskVo();
            portalTaskVo.setTotal(Long.toString(result.getTotalElements()));
            portalTaskVo.setMoreUrl("http://107.255.18.100:8888/mytask/todotask/list?page=1&size=10&total=0");
            portalTaskVo.setOpenType("1");
            portalTaskVo.setPlatCode("SRCPS");
            if (dataType == null || "".equals(dataType) || dataType.equals("1")) {
                List<PortalTaskDataVo> list = new ArrayList<>();
                for (TaskDto taskDto : result) {
                    PortalTaskDataVo portalTaskDataVo = new PortalTaskDataVo();
                    portalTaskDataVo.setAppCode("SRCPS");
                    portalTaskDataVo.setDate(taskDto.getCreateTime());
                    portalTaskDataVo.setTitle(taskDto.getName());
                    if (taskDto.getPriority() <= 50) {
                        portalTaskDataVo.setUrgentLev("99");
                    } else if (taskDto.getPriority() <= 100 && taskDto.getPriority() > 50) {
                        portalTaskDataVo.setUrgentLev("03");
                    } else if (taskDto.getPriority() <= 150 && taskDto.getPriority() > 100) {
                        portalTaskDataVo.setUrgentLev("02");
                    } else if (taskDto.getPriority() <= 200 && taskDto.getPriority() > 150) {
                        portalTaskDataVo.setUrgentLev("01");
                    } else {
                        portalTaskDataVo.setUrgentLev("00");
                    }
                    String bizUrl = taskFacade.getBizUrl(taskDto.getId());
                    portalTaskDataVo.setUrl("http://107.255.18.100:8888/#" + bizUrl);
                    list.add(portalTaskDataVo);
                }
                portalTaskVo.setData(list);
            }
            ObjectMapper om = new ObjectMapper();
            String returnJson = null;
            try {
                returnJson = "workItem_SRCPS(" + om.writeValueAsString(portalTaskVo) + ")";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(returnJson);
            StringBuffer data = new StringBuffer("workItem_SRCPS" + "(");
            data.append(returnJson);
            response.setHeader("Content-type", "text/plain");
            data.append(")");
            System.out.println(data.toString());
            try {
                response.getWriter().write(data.toString());
            } catch (IOException e) {
                log.error("待办数据获取异常" + e);
            }
            System.out.println(data);*/
            return data;
        }
    }

}
