package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgProjectScheduleFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectScheduleUserFacade;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleUserDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleUserDto;
import com.realfinance.sofa.cg.model.cg.CgProjectScheduleUserVo;
import com.realfinance.sofa.cg.model.cg.CgProjectScheduleVo;
import com.realfinance.sofa.cg.service.mapstruct.CgProjectScheduleMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgProjectScheduleUserMapper;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Tag(name = "项目进度对象关联")
@RequestMapping("/cg/core/projectscheduleuser")
public class ProjectScheduleUserController {

    private static final Logger log = LoggerFactory.getLogger(ProjectScheduleUserController.class);

//    public static final String MENU_CODE_ROOT = "purproj";
//    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
//    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
//    public static final String MENU_CODE_RETURN = MENU_CODE_ROOT + ":return"; // 退回
//    public static final String MENU_CODE_STARTPROC = MENU_CODE_ROOT + ":startproc";

    @SofaReference(interfaceType = CgProjectScheduleUserFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgProjectScheduleUserFacade cgProjectScheduleUserFacade;
    @SofaReference(interfaceType = CgProjectScheduleFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgProjectScheduleFacade cgProjectScheduleFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgProjectScheduleUserMapper cgProjectScheduleUserMapper;

    @Resource
    private CgProjectScheduleMapper cgProjectScheduleMapper;

    @PostMapping("updatestatus")
    @Operation(summary = "修改是否关注")
    //@PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProjectScheduleController).MENU_CODE_VIEW)")
    public ResponseEntity<Void> updateStatus(@Validated @RequestBody CgProjectScheduleVo vo) {
        CgProjectScheduleDetailsSaveDto saveDto = cgProjectScheduleMapper.toSaveDto(vo);
        Integer id=cgProjectScheduleUserFacade.save(saveDto);
        return ResponseEntity.ok().build();
    }

}
