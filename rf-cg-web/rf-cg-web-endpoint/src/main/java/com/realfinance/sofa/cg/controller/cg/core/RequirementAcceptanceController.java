package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgRequirementFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementFlowableVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementVo;
import com.realfinance.sofa.cg.model.flow.FlowTaskVo;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.UserQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.cg.service.mapstruct.CgRequirementMapper;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.facade.TaskFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import com.realfinance.sofa.system.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Tag(name = "采购需求受理")
@RequestMapping("/cg/core/reqacceptance")
public class RequirementAcceptanceController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(RequirementAcceptanceController.class);

    public static final String MENU_CODE_ROOT = "reqaccept";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_ASSIGN = MENU_CODE_ROOT + ":assign"; // 指派采购经办员
    public static final String MENU_CODE_ACCEPT = MENU_CODE_ROOT + ":accept"; // 受理
    public static final String MENU_CODE_RETURN = MENU_CODE_ROOT + ":return"; // 退回

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @SofaReference(interfaceType = CgRequirementFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgRequirementFacade cgRequirementFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = TaskFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private TaskFacade taskFacade;
    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgRequirementMapper cgRequirementMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DepartmentMapper departmentMapper;

    @GetMapping("list")
    @Operation(summary = "查询采购需求受理列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementAcceptanceController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgRequirementVo>> list(@ParameterObject CgRequirementQueryCriteria queryCriteria,
                                                      Pageable pageable) {
        if (queryCriteria == null) {
            queryCriteria = new CgRequirementQueryCriteria();
        }
        queryCriteria.setAcceptStatus("DCL");
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgRequirementDto> result = cgRequirementFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result.map(cgRequirementMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询采购受理详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementAcceptanceController).MENU_CODE_VIEW)")
    public ResponseEntity<CgRequirementVo> getDetailsById(@Parameter(description = "采购需求ID") @RequestParam Integer id, @AuthenticationPrincipal Authentication authentication) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgRequirementDetailsDto result = cgRequirementFacade.getDetailsById(id);
        CgRequirementVo vo = cgRequirementMapper.toVo(result);
        //获取当前流程的信息
        vo.setFlowInfo(getFlowInfo(id.toString()));
        if (vo.getRequirementAtts() != null) {
            for (CgAttVo requirementAtt : vo.getRequirementAtts()) {
                FileToken fileToken = FileTokens.create(requirementAtt.getPath(), requirementAtt.getName(), authentication.getName());
                requirementAtt.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        return ResponseEntity.ok(vo);
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgRequirementVo vo) {

        List<CgRequirementOaDatumDto> requirementOaData = vo.getRequirementOaData();
        if (requirementOaData == null || requirementOaData.isEmpty()) {
            throw new RuntimeException("缺少立项审批号");
        }
        if (vo.getPurchasePlan() == null) {
            vo.setPlanStatus("JHW");
        } else {
            vo.setPlanStatus("JHN");
        }
        CgRequirementDetailsSaveDto saveDto = cgRequirementMapper.toSaveDto(vo);
        Integer id = cgRequirementFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }


    @PostMapping("assign")
    @Operation(summary = "指派经办人")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementAcceptanceController).MENU_CODE_ASSIGN)")
    public ResponseEntity<Void> assign(@RequestBody @Validated(FlowTaskVo.Complete.class) CgRequirementFlowableVo vo,
                                       @AuthenticationPrincipal AuthInfo authInfo) {
        UserVo userVo = vo.getCgRequirementVo().getOperator();
        String comment = String.format("%s(%s) 于 %s 指派 %s(%s) 为经办人",
                authInfo.getUser().getRealname(),authInfo.getUser().getUsername(),
                LocalDateTime.now().toString(),userVo.getRealname(),userVo.getUsername());
        dataRuleHelper.installDataRule(MENU_CODE_ASSIGN);
        cgRequirementFacade.assignOperator(vo.getCgRequirementVo().getId(),userVo.getId(),comment);
        //调用审批流-完成任务接口
        FlowApi.super.flowCompleteTask(vo.getFlowTaskVo());
        return ResponseEntity.ok().build();
    }

    @PostMapping("return")
    @Operation(summary = "退回需求")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementAcceptanceController).MENU_CODE_RETURN)")
    public ResponseEntity<Void> returnReq(@RequestBody CgRequirementVo vo,
                                          @AuthenticationPrincipal AuthInfo authInfo,
                                          @Parameter(description = "ID") @RequestParam String processInstanceId) {
        String reason = String.format("%s(%s) 于 %s 退回，原因：%s",
                authInfo.getUser().getRealname(),authInfo.getUser().getUsername(),
                LocalDateTime.now().toString(),vo.getReason());
        dataRuleHelper.installDataRule(MENU_CODE_RETURN);
        cgRequirementFacade.updateAcceptStatus(vo.getId(),"TH",reason);
        Map<String, String> body=new HashMap<>();
        body.put("comment",vo.getReason());
        FlowApi.super.flowDeleteProcess(processInstanceId,body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("accept")
    @Operation(summary = "经办人受理")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementAcceptanceController).MENU_CODE_ACCEPT)")
    public ResponseEntity<Void> accept(@RequestBody @Validated(FlowTaskVo.Complete.class) CgRequirementFlowableVo vo, @AuthenticationPrincipal AuthInfo authInfo) {
        CgRequirementDto cgRequirementDto = cgRequirementFacade.getById(vo.getCgRequirementVo().getId());
        if (cgRequirementDto.getOperatorUserId() == null) {
            throw new RuntimeException("未指派经办人");
        }
        if (!Objects.equals(cgRequirementDto.getOperatorUserId(), authInfo.getUser().getId())) {
            throw new RuntimeException("非指派的经办人");
        }
        dataRuleHelper.installDataRule(MENU_CODE_ACCEPT);
        cgRequirementFacade.updateAcceptStatus(vo.getCgRequirementVo().getId(),"TG",null);
        FlowApi.super.flowCompleteTask(vo.getFlowTaskVo());
        return ResponseEntity.ok().build();
    }

    //分派,只能分派给采购中心经办人
    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照", description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementAcceptanceController).MENU_CODE_ASSIGN)")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询用户参照", description = "非系统租户只返回当前登录的法人下的用户")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.RequirementAcceptanceController).MENU_CODE_ASSIGN)")
    public ResponseEntity<Page<UserVo>> queryUserRefer(UserQueryCriteriaRequest queryCriteria,
                                                       Pageable pageable) {
        Page<UserDto> result = systemQueryFacade.queryUserRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @Override
    public ResponseEntity<String> flowStartProcess(@Parameter(description = "ID") @RequestParam Integer id,
                                                   @RequestBody(required = false) Map<String, String> formData) {
        CgRequirementDto dto = cgRequirementFacade.getById(id);
        String processInstanceId = getFlowFacade().startProcess(getBusinessCode(), id.toString(), formData,dto.getName());
        return ResponseEntity.ok(processInstanceId);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(FlowCallbackRequest request) {
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }

    @Override
    public String getBusinessCode() {
        return MENU_CODE_ROOT;
    }

    @Override
    public FlowFacade getFlowFacade() {
        return flowFacade;
    }
}
