package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierMapper;
import com.realfinance.sofa.cg.sup.facade.CgMassMessagingFacade;
import com.realfinance.sofa.cg.sup.model.CgMassMessagingQueryCriteria;
import com.realfinance.sofa.cg.sup.model.MassMessagingDto;
import com.realfinance.sofa.cg.sup.model.MassMessagingSaveDto;
import com.realfinance.sofa.cg.sup.model.SupplierContactsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping("/cg/sup/MassMessaging")
@Tag(name = "群发消息")
public class MassMessagingController {

    private static final Logger log = LoggerFactory.getLogger(MassMessagingController.class);

    //ToDo  不知消息是否需要设置权限
    public static final String MENU_CODE_ROOT = "massmessaging";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    @SofaReference(interfaceType = CgMassMessagingFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgMassMessagingFacade cgMassMessagingFacade;
    @Resource
    private CgSupplierMapper cgSupplierMapper;


    @GetMapping("querysuppliercontactsrefer")
    @Operation(summary = "查询供应商联系人参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<SupplierContactsDto>> querySupplierRefer(Pageable pageable) {
        Page<SupplierContactsDto> page = cgMassMessagingFacade.list(pageable);
        return ResponseEntity.ok(page);
    }


    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.MassMessagingController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody MassMessagingSaveDto saveDto) {
        Integer id = cgMassMessagingFacade.save(saveDto);
        return ResponseEntity.ok(id);

    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.MassMessagingController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<MassMessagingDto>> list(Pageable pageable, @ParameterObject CgMassMessagingQueryCriteria queryCriteria) {
        Page<MassMessagingDto> page = cgMassMessagingFacade.list(pageable, queryCriteria);
        return ResponseEntity.ok(page);
    }

    @GetMapping("getdetailsbyid")
    @Operation(summary = "详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.MassMessagingController).MENU_CODE_VIEW)")
    public ResponseEntity<MassMessagingDto> findById(@Parameter(description = "消息ID") @RequestParam Integer id) {
        MassMessagingDto dto = cgMassMessagingFacade.findById(id);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.MassMessagingController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@RequestParam Set<Integer> ids) {
        cgMassMessagingFacade.delete(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("send")
    @Operation(summary = "发送")
    public ResponseEntity send(@Parameter(description = "消息ID") @RequestParam Integer id) {
        cgMassMessagingFacade.send(id);
        return ResponseEntity.ok().build();
    }

}
