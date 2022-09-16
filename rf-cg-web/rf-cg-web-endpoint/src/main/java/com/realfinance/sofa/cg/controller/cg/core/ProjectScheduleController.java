package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgProjectScheduleFacade;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleQueryCriteria;
import com.realfinance.sofa.cg.model.cg.CgProjectScheduleVo;
import com.realfinance.sofa.cg.service.mapstruct.CgProjectScheduleMapper;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Tag(name = "项目进度")
@RequestMapping("/cg/core/projectschedule")
public class ProjectScheduleController{

    private static final Logger log = LoggerFactory.getLogger(ProjectScheduleController.class);

//    public static final String MENU_CODE_ROOT = "purproj";
//    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
//    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
//    public static final String MENU_CODE_RETURN = MENU_CODE_ROOT + ":return"; // 退回
//    public static final String MENU_CODE_STARTPROC = MENU_CODE_ROOT + ":startproc";

    @SofaReference(interfaceType = CgProjectScheduleFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgProjectScheduleFacade cgProjectScheduleFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgProjectScheduleMapper cgProjectScheduleMapper;

    @GetMapping("list")
    @Operation(summary = "查询项目进度列表")
    //@PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectScheduleController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgProjectScheduleVo>> list(@ParameterObject CgProjectScheduleQueryCriteria queryCriteria,
                                                          Pageable pageable) {
        //dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgProjectScheduleDto> result = cgProjectScheduleFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("attention").descending()));
        return ResponseEntity.ok(result.map(cgProjectScheduleMapper::toVo));
    }

}
