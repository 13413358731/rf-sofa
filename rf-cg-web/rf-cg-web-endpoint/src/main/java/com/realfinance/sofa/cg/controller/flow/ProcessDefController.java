package com.realfinance.sofa.cg.controller.flow;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.flow.BizModelSaveRequest;
import com.realfinance.sofa.cg.model.flow.BizModelVo;
import com.realfinance.sofa.cg.model.flow.BizVo;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.service.mapstruct.FlowBizMapper;
import com.realfinance.sofa.cg.service.mapstruct.FlowBizModelMapper;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.flow.facade.ProcessDefFacade;
import com.realfinance.sofa.flow.model.BizDto;
import com.realfinance.sofa.flow.model.BizModelDto;
import com.realfinance.sofa.flow.model.ProcessDefinitionDto;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "流程设计")
@RequestMapping("/process/processdef")
public class ProcessDefController {

    private static final Logger log = LoggerFactory.getLogger(ProcessDefController.class);

    public static final String MENU_CODE_ROOT = "processdef";

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @SofaReference(interfaceType = ProcessDefFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ProcessDefFacade processDefFacade;

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private FlowBizMapper flowBizMapper;
    @Resource
    private FlowBizModelMapper flowBizModelMapper;

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照", description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @GetMapping("querybizrefer")
    @Operation(summary = "查询业务参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<BizVo>> queryBizRefer(@Parameter(description = "搜索条件") @RequestParam(value = "filter",required = false) String filter,
                                                     Pageable pageable) {
        Page<BizDto> result = processDefFacade.queryBizRefer(filter, pageable);
        return ResponseEntity.ok(result.map(flowBizMapper::bizDto2BizVo));
    }


    @GetMapping("listbizmodelbydepartmentid")
    @Operation(summary = "查询部门下的业务流程模型")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<BizModelVo>> listBizModelByDepartmentId(@Parameter(description = "部门ID") @RequestParam Integer departmentId,
                                                                       Pageable pageable) {
        Page<BizModelDto> result = processDefFacade.listBizModelByDepartmentId(departmentId,pageable);
        return ResponseEntity.ok(result.map(flowBizModelMapper::bizModelDto2BizModelVo));
    }

    @GetMapping("listprocessdefinitionbybizmodelid")
    @Operation(summary = "查询流程定义历史版本")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ProcessDefinitionDto>> listProcessDefinitionByBizModelId(@Parameter(description = "业务模型ID") @RequestParam Integer bizModelId,
                                                                                        Pageable pageable) {
        Page<ProcessDefinitionDto> result = processDefFacade.listProcessDefinitionByBizModelId(bizModelId,pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "getmodelresourcebyprocessdefinitionid", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "查询流程定义图")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> getModelResourceByProcessDefinitionId(@Parameter(description = "流程定义ID") @RequestParam String processDefinitionId) {
        byte[] result = processDefFacade.getModelResourceByProcessDefinitionId(processDefinitionId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("savebizmodel")
    @Operation(summary = "保存业务流程模型")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Integer> saveBizModel(@Validated @RequestBody BizModelSaveRequest bizModelSaveRequest) {
        Integer id = processDefFacade.saveBizModel(flowBizModelMapper.bizModelSaveRequest2BizModelSaveDto(bizModelSaveRequest));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("deletebizmodel")
    @Operation(summary = "删除业务流程模型")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteBizModel(@Parameter(description = "业务流程模型ID") @RequestParam Set<Integer> bizModelId) {
        processDefFacade.deleteBizModel(bizModelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteprocessdefinition")
    @Operation(summary = "删除流程定义")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteProcessDefinition(@Parameter(description = "流程定义ID") @RequestParam String processDefinitionId,
                                                     @Parameter(description = "联级删除所有数据") @RequestParam(required = false) Boolean cascade) {
        processDefFacade.deleteProcessDefinition(processDefinitionId,cascade);
        return ResponseEntity.ok().build();
    }

    @PostMapping("activate")
    @Operation(summary = "激活", description = "根据流程定义ID激活流程定义")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> activate(@Parameter(description = "流程定义ID") @RequestParam String processDefinitionId,
                                      @Parameter(description = "是否联级激活流程实例") @RequestParam Boolean activateProcessInstances) {
        processDefFacade.activate(processDefinitionId,activateProcessInstances);
        return ResponseEntity.ok().build();
    }

    @PostMapping("suspend")
    @Operation(summary = "挂起", description = "根据流程定义ID挂起流程定义")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> suspend(@Parameter(description = "流程定义ID") @RequestParam String processDefinitionId,
                                     @Parameter(description = "是否联级挂起流程实例") @RequestParam Boolean suspendProcessInstances) {
        processDefFacade.suspend(processDefinitionId,suspendProcessInstances);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deletelastdeploy")
    @Operation(summary = "删除最后一次部署", description = "删除最后一次部署并且联级删除实例和历史")
    @PreAuthorize("isAuthenticated()")
    @Deprecated
    public ResponseEntity<?> deleteLastDeploy(@Parameter(description = "业务流程模型ID") @RequestParam Integer bizModelId) {
        processDefFacade.deleteLastDeploy(bizModelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("deploy")
    @Operation(summary = "部署")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deploy(@Parameter(description = "业务流程模型ID") @RequestParam Integer bizModelId) {
        processDefFacade.deploy(bizModelId);
        return ResponseEntity.ok().build();
    }
}
