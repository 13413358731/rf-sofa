package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgPurchaseCatalogFacade;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogSaveDto;
import com.realfinance.sofa.cg.model.cg.CgPurchaseCatalogTreeVo;
import com.realfinance.sofa.cg.model.cg.CgPurchaseCatalogVo;
import com.realfinance.sofa.cg.service.mapstruct.CgPurchaseCatalogMapper;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelSaveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Tag(name = "采购目录")
@RequestMapping("/cg/core/purcatalog")
public class PurchaseCatalogController {

    private static final Logger log = LoggerFactory.getLogger(PurchaseCatalogController.class);

    public static final String MENU_CODE_ROOT = "purcatalog";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";

    @SofaReference(interfaceType = CgPurchaseCatalogFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseCatalogFacade cgPurchaseCatalogFacade;

    @Resource
    private CgPurchaseCatalogMapper cgPurchaseCatalogMapper;

    @GetMapping("list")
    @Operation(summary = "查询所有数据")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchaseCatalogController).MENU_CODE_VIEW)")
    public ResponseEntity<List<CgPurchaseCatalogTreeVo>> list(@ParameterObject CgPurchaseCatalogQueryCriteria queryCriteria) {
        List<CgPurchaseCatalogDto> result = cgPurchaseCatalogFacade.list(queryCriteria);
        return ResponseEntity.ok(cgPurchaseCatalogMapper.buildTree(result));
    }

    @PostMapping("save")
    @Operation(summary = "保存采购目录")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.PurchaseCatalogController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(/*@Validated(CgPurchaseCatalogVo.Save.class)*/
                                        @RequestBody CgPurchaseCatalogVo vo) {
        CgPurchaseCatalogSaveDto saveDto = cgPurchaseCatalogMapper.toSaveDto(vo);
        Integer id = cgPurchaseCatalogFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

}
