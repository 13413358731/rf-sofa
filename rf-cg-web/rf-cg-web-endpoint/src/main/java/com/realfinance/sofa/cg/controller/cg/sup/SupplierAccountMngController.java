package com.realfinance.sofa.cg.controller.cg.sup;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.cg.CgSupplierAccountQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.cg.CgSupplierAccountVo;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierAccountMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAccountFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountCreateDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountDto;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Tag(name = "供应商账号管理")
@RequestMapping("/cg/sup/supplieraccountmng")
public class SupplierAccountMngController {

    private static final Logger log = LoggerFactory.getLogger(SupplierAccountMngController.class);

    public static final String MENU_CODE_ROOT = "supplieraccountmng";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";

    @SofaReference(interfaceType = CgSupplierAccountFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAccountFacade cgSupplierAccountFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;

    @Resource
    private CgSupplierAccountMapper cgSupplierAccountMapper;

    @GetMapping("list")
    @Operation(summary = "查询供应商账户列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierAccountMngController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgSupplierAccountVo>> list(CgSupplierAccountQueryCriteriaRequest queryCriteria,
                                                          Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgSupplierAccountDto> result = cgSupplierAccountFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierAccountMapper::toVo));
    }

    @PostMapping("create")
    @Operation(summary = "新建供应商账号")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierAccountMngController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> createAccount(@Schema(implementation = CgSupplierAccountVo.Create.class)
                                                 @Validated(CgSupplierAccountVo.Create.class)
                                                 @RequestBody CgSupplierAccountVo vo) {
        CgSupplierAccountCreateDto dto = cgSupplierAccountMapper.toSaveDto(vo);
        dto.setType("INVITE");
        Integer id = cgSupplierAccountFacade.createAccount(dto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("resetpassword")
    @Operation(summary = "修改密码")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierAccountMngController).MENU_CODE_SAVE)")
    public ResponseEntity<?> resetPassword(@Schema(implementation = CgSupplierAccountVo.ResetPassword.class)
                                           @Validated(CgSupplierAccountVo.ResetPassword.class)
                                           @RequestBody CgSupplierAccountVo vo) {
        cgSupplierAccountFacade.resetPassword(vo.getUsername(),vo.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("updateenabled")
    @Operation(summary = "更新可用状态")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.SupplierAccountMngController).MENU_CODE_SAVE)")
    public ResponseEntity<?> updateEnabled(@Schema(implementation = CgSupplierAccountVo.UpdateEnabled.class)
                                           @Validated(CgSupplierAccountVo.UpdateEnabled.class)
                                           @RequestBody CgSupplierAccountVo vo) {
        cgSupplierAccountFacade.updateEnabled(vo.getUsername(),vo.getEnabled());
        return ResponseEntity.ok().build();
    }

}
