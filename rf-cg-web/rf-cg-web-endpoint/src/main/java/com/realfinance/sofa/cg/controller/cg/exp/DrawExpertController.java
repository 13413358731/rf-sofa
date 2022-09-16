package com.realfinance.sofa.cg.controller.cg.exp;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.*;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "抽取专家")
@RequestMapping("/cg/core/drawexperts")
public class DrawExpertController implements FlowApi{
    private static final Logger log = LoggerFactory.getLogger(DrawExpertController.class);

    public static final String MENU_CODE_ROOT = "expertsextract";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";

    @SofaReference(interfaceType = CgDrawExpertFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgDrawExpertFacade cgDrawExpertFacade;
    @SofaReference(interfaceType = CgExpertLabelFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelFacade cgExpertLabelFacade;
    @SofaReference(interfaceType = CgExpertLabelTypeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelTypeFacade cgExpertLabelTypeFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;
    @SofaReference(interfaceType = CgDrawExpertListFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgDrawExpertListFacade cgDrawExpertListFacade;
    @SofaReference(interfaceType = CgExpertFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertFacade cgExpertFacade;
    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectFacade cgProjectFacade;
    @SofaReference(interfaceType = CgMeetingFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgMeetingFacade cgMeetingFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgDrawExpertMapper cgDrawExpertMapper;

    @Resource
    private CgExpertLabelMapper cgExpertLabelMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private CgDrawExpertListMapper cgDrawExpertListMapper;

    @Resource
    private CgExpertMapper cgExpertMapper;

    @Resource
    private CgProjectMapper cgProjectMapper;

    @Resource
    private CgDrawExpertRuleMapper cgDrawExpertRuleMapper;

    @GetMapping("listbig")
    @Operation(summary = "查询专家抽取主表")
    public ResponseEntity<Page<CgDrawExpertVo>> listBig(CgDrawExpertQueryCriteriaRequest queryCriteria,
                                                        Pageable pageable) {
        Page<CgDrawExpertDto> result = cgDrawExpertFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result.map(cgDrawExpertMapper::toVo));
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询专家抽取详情")
    public ResponseEntity<CgDrawExpertVo> getDetailsById(@Parameter(description = "专家抽取主表id") @RequestParam Integer id) {
        CgDrawExpertDetailsDto result = cgDrawExpertFacade.getDetailsById(id);
        CgDrawExpertVo vo = cgDrawExpertMapper.toVo(result);
        vo.setFlowInfo(getFlowInfo(id.toString()));

        return ResponseEntity.ok(vo);
    }

    @GetMapping("getidbyprojectid")
    @Operation(summary = "根据执行ID查询专家抽取的ID")
    public ResponseEntity<Integer> getDetailsByProjectExecutionId(@Parameter(description = "采购方案ID") @RequestParam Integer projectId) {
        Integer id = cgDrawExpertFacade.getIdByProjectId(projectId);
        return ResponseEntity.ok(id);
    }

    @PostMapping("savebig")
    @Operation(summary = "保存专家抽取主表")
    public ResponseEntity<Integer> saveBig(@Validated(CgDrawExpertVo.Save.class)
                                           @RequestBody CgDrawExpertVo vo,
                                           Authentication authentication) {
        //专家组成描述
        vo.setDescription(vo.getProjectId().getPurGroup());
        CgDrawExpertSaveDto saveDto = cgDrawExpertMapper.toSaveDto(vo);
        Integer id = cgDrawExpertFacade.save(saveDto);
        //新增数据时 同时启动抽取专家通知流程
        if (saveDto.getId()==null){
            getFlowFacade().startProcessToUserId(getBusinessCode(), id.toString(), null,Integer.parseInt(authentication.getName()),vo.getProjectId().getName());
        }
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("deletebig")
    @Operation(summary = "删除专家抽取主表")
    public ResponseEntity<?> deleteBig(@Parameter(description = "专家ID") @RequestParam Set<Integer> id) {
        cgDrawExpertFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("listSmall")
    @Operation(summary = "查询专家抽取表")
    public ResponseEntity<Page<CgDrawExpertListVo>> listSmall(CgDrawExpertListQueryCriteriaRequest queryCriteria,
                                                              Pageable pageable) {
        Page<CgDrawExpertListDto> result = cgDrawExpertListFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgDrawExpertListMapper::toVo));
    }

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照",description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @GetMapping("queryprojectrefer")
    @Operation(summary = "查询采购方案参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgProjectVo>> queryProjectRefer(CgProjectQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgProjectSmallDto> result = cgProjectFacade.queryRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(cgProjectMapper::toVo));
    }

    @GetMapping("queryDrawExpertrefer")
    @Operation(summary = "直接指派专家")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgExpertVo>> queryLabelRefer(CgExpertQueryCriteriaRequest queryCriteria,
                                                            Pageable pageable) {
        Page<CgExpertDto> result = cgExpertFacade.queryRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(cgExpertMapper::toVo));
    }

    //todo 前端对接专家抽取关联相关会议
    @GetMapping("finish")
    @Operation(summary = "确定完成(关联相关会议)专家抽取下面有方案Id")
    public ResponseEntity<Integer> finish(@Parameter(description = "专家抽取id") @RequestParam Integer drawexpId,@Parameter(description = "采购方案id") @RequestParam Integer projId) {
        CgDrawExpertListQueryCriteria criteria = new CgDrawExpertListQueryCriteria();
        criteria.setIsAttend(1);
        criteria.setDrawExpertId(drawexpId);
        List<CgDrawExpertListDto> result = cgDrawExpertListFacade.list(criteria);
        cgMeetingFacade.saveMeeting(projId,result);
        Integer resultId = cgDrawExpertFacade.update(drawexpId);
        return ResponseEntity.ok(resultId);
    }

    @PostMapping("savesmall")
    @Operation(summary = "保存直接指派专家")
    public ResponseEntity<Integer> saveSmall(@RequestParam Integer id,
                                             @Validated(CgDrawExpertListVo.Save.class) @RequestBody CgDrawExpertListVo vo) {
        CgDrawExpertListSaveDto saveDto = cgDrawExpertListMapper.toSaveDto(vo);
        return ResponseEntity.ok(cgDrawExpertListFacade.save(id,saveDto));
    }

    @PostMapping("saveRandomly")
    @Operation(summary = "条件抽取专家")
    public ResponseEntity<Integer> drawRandomlySave(@RequestParam Integer id) {
        Integer integer = cgDrawExpertListFacade.saveRandomly(id);
        return ResponseEntity.ok(integer);
    }

    @DeleteMapping("deletesmall")
    @Operation(summary = "删除抽取专家")
    public ResponseEntity<?> deleteSmall(@Parameter(description = "专家ID") @RequestParam Set<Integer> id) {
        cgDrawExpertListFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("savedrawexpertrule")
    @Operation(summary = "保存专家抽取规则")
    public ResponseEntity<Integer> saveDrawExpertRule(@RequestBody List<CgDrawExpertRuleVo> vo, @RequestParam Integer drawExpertId) {
        List<CgDrawExpertRuleDto> cgDrawExpertRuleDtos = new ArrayList<>();
        for (CgDrawExpertRuleVo cgDrawExpertRuleVo : vo) {
            if(cgDrawExpertRuleVo.getExpertDeptId()!=null&&cgDrawExpertRuleVo.getExpertCount()!=null){
                CgDrawExpertRuleDto saveDto = cgDrawExpertRuleMapper.toSaveDto(cgDrawExpertRuleVo);
                cgDrawExpertRuleDtos.add(saveDto);
            }
        }
        Integer id = cgDrawExpertFacade.save(cgDrawExpertRuleDtos,drawExpertId);
        return ResponseEntity.ok(id);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
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
