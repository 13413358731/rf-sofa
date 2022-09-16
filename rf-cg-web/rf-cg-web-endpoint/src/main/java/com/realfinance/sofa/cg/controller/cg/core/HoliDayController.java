package com.realfinance.sofa.cg.controller.cg.core;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgHoliDayFacade;
import com.realfinance.sofa.cg.core.model.CgHoliDayQueryCriteria;
import com.realfinance.sofa.cg.core.model.HoliDayDto;
import com.realfinance.sofa.cg.core.model.HoliDaySaveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "节假日")
@RequestMapping("/cg/core/holiDay")
public class HoliDayController {

    private static final Logger log = LoggerFactory.getLogger(HoliDayController.class);

    //TODO  不知是否需要设置权限
    public static final String MENU_CODE_ROOT = "holiday";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";

    @SofaReference(interfaceType = CgHoliDayFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgHoliDayFacade cgHoliDayFacade;


    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.HoliDayController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody HoliDaySaveDto saveDto) {
        Integer id = cgHoliDayFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.HoliDayController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<HoliDayDto>> list(@ParameterObject CgHoliDayQueryCriteria queryCriteria, Pageable pageable) {
        Page<HoliDayDto> page = cgHoliDayFacade.list(pageable, queryCriteria);
        return ResponseEntity.ok(page);
    }


}
