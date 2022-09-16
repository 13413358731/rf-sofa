package com.realfinance.sofa.cg.controller.cg.exp;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgDrawExpertFacade;
import com.realfinance.sofa.cg.core.facade.CgExpertConfirmFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectFacade;
import com.realfinance.sofa.cg.core.model.CgDrawExpertDetailsDto;
import com.realfinance.sofa.cg.core.model.CgExpertConfirmDto;
import com.realfinance.sofa.cg.core.model.CgExpertConfirmSaveDto;
import com.realfinance.sofa.cg.core.model.CgProjectDetailsDto;
import com.realfinance.sofa.cg.model.cg.CgExpertConfirmFlowableVo;
import com.realfinance.sofa.cg.model.cg.CgExpertConfirmQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.cg.CgExpertConfirmVo;
import com.realfinance.sofa.cg.model.cg.CgProjectVo;
import com.realfinance.sofa.cg.service.mapstruct.CgExpertConfirmMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgProjectMapper;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "专家确认")
@RequestMapping("/cg/core/expertsconfirm")
public class ExpertConfirmController implements FlowApi {

    private static final Logger log = LoggerFactory.getLogger(ExpertConfirmController.class);

    public static final String MENU_CODE_ROOT = "expertconfirm";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";

    @SofaReference(interfaceType = CgDrawExpertFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgDrawExpertFacade cgDrawExpertFacade;
    @SofaReference(interfaceType = CgExpertConfirmFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertConfirmFacade cgExpertConfirmFacade;
    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectFacade cgProjectFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @Resource
    private CgExpertConfirmMapper cgExpertConfirmMapper;
    @Resource
    private CgProjectMapper cgProjectMapper;


    @GetMapping("list")
    @Operation(summary = "专家确认表")
    public ResponseEntity<Page<CgExpertConfirmVo>> list(CgExpertConfirmQueryCriteriaRequest queryCriteria,
                                                        Pageable pageable) {
        Page<CgExpertConfirmDto> result = cgExpertConfirmFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        Page<CgExpertConfirmVo> map = result.map(cgExpertConfirmMapper::toVo);
        for (CgExpertConfirmVo cgExpertConfirmVo : map.getContent()) {
            CgProjectVo cgProjectVo = cgProjectMapper.toVo(cgProjectFacade.getDetailsById(cgExpertConfirmVo.getProjectId()));
            cgExpertConfirmVo.setCgProjectVo(cgProjectVo);
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询专家确认详情")
    public ResponseEntity<CgExpertConfirmVo> getDetailsById(@Parameter(description = "专家确认表ID") @RequestParam Integer id) {
        CgExpertConfirmDto result = cgExpertConfirmFacade.getDetailsById(id);
        CgProjectDetailsDto projectDetailsDto = cgProjectFacade.getDetailsById(result.getProjectId());
        CgProjectVo cgProjectVo = cgProjectMapper.toVo(projectDetailsDto);
        CgExpertConfirmVo vo = cgExpertConfirmMapper.toVo(result);
        vo.setCgProjectVo(cgProjectVo);
        vo.setFlowInfo(getFlowInfo(id.toString()));
        return ResponseEntity.ok(vo);
    }

    @PostMapping("notifyexpert")
    @Operation(summary = "通知抽取专家")
    public ResponseEntity<?> notifyExperts(@RequestParam Integer id,@RequestParam Set<Integer> ids) {
        List<CgExpertConfirmDto> list = cgExpertConfirmFacade.notifyExperts(id, ids);
        CgDrawExpertDetailsDto result = cgDrawExpertFacade.getDetailsById(id);
        CgProjectDetailsDto cgProjectDetailsDto = cgProjectFacade.getDetailsById(result.getProjectId());
        //同时启动内部通知  通知专家
        for (CgExpertConfirmDto dto : list) {
            getFlowFacade().startProcessToUserId(getBusinessCode(), dto.getId().toString(), null,dto.getExpertUserId(),cgProjectDetailsDto.getName());
        }
        return ResponseEntity.ok().build();
    }

//    @PostMapping("notifyexpert2")
//    @Operation(summary = "通知抽取专家")
//    public ResponseEntity<?> notifyExperts2(@RequestParam Integer id) {
//        cgExpertConfirmFacade.notifyExperts2(id);
//        return ResponseEntity.ok().build();
//    }




    @PostMapping("confirm")
    @Operation(summary = "专家确认")
    public ResponseEntity<Integer> saveBig(@RequestBody CgExpertConfirmFlowableVo vo) {
        CgExpertConfirmSaveDto saveDto = cgExpertConfirmMapper.toSaveDto(vo.getCgExpertConfirmVo());
        Integer id = cgExpertConfirmFacade.confirm(saveDto);
        //调用审批流完成任务接口
        if (vo.getFlowTaskVo()!=null){
            FlowApi.super.flowCompleteTask(vo.getFlowTaskVo());
        }
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
