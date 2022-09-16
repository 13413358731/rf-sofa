package com.realfinance.sofa.cg.controller.cg.core;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgProductLibraryFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseCatalogFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.service.mapstruct.CgProjectMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgPurchaseCatalogMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgSupplierMapper;
import com.realfinance.sofa.cg.service.mapstruct.ProductLibraryMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierSmallDto;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.ImportTollProductLibrary;
import com.realfinance.sofa.cg.util.POIUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "产品库")
    @RequestMapping("/cg/core/productlibrary")
public class ProductLibraryController {
    private static final Logger log = LoggerFactory.getLogger(ProductLibraryController.class);

    public static final String MENU_CODE_ROOT = "productlibrary";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";

    @SofaReference(interfaceType = CgProductLibraryFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProductLibraryFacade cgProductLibraryFacade;
    @Resource
    private ProductLibraryMapper productLibraryMapper;
    @SofaReference(interfaceType = CgPurchaseCatalogFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseCatalogFacade cgPurchaseCatalogFacade;
    @Resource
    private CgPurchaseCatalogMapper cgPurchaseCatalogMapper;
    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectFacade cgProjectFacade;
    @Resource
    private CgProjectMapper cgProjectMapper;
    @Resource
    private DataRuleHelper dataRuleHelper;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;
    @Resource
    private CgSupplierMapper cgSupplierMapper;

    @Autowired
    private ImportTollProductLibrary importTollProductLibrary;

    @GetMapping("listPurchaseCatalog")
    @Operation(summary = "查询采购目录所有数据")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CgPurchaseCatalogTreeVo>> list(@ParameterObject CgPurchaseCatalogQueryCriteria queryCriteria) {
        List<CgPurchaseCatalogDto> result = cgPurchaseCatalogFacade.list(queryCriteria);
        return ResponseEntity.ok(cgPurchaseCatalogMapper.buildTree(result));
    }


    @GetMapping("querysupplierrefer")
    @Operation(summary = "查询供应商参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgSupplierVo>> querySupplierRefer(CgSupplierQueryCriteriaRequest queryCriteria,
                                                                 Pageable pageable) {
        Page<CgSupplierSmallDto> result = cgSupplierFacade.queryRefer(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgSupplierMapper::toVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProductLibraryController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody CgProductLibrarySaveRepuest saveRepuest) {
        CgProductSaveDto saveDto = productLibraryMapper.toSaveDto(saveRepuest);
        Integer id = cgProductLibraryFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("listProject")
    @Operation(summary = "查询采购方案列表")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CgProjectVo>> list(@ParameterObject CgProjectQueryCriteria queryCriteria,
                                                  Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgProjectDto> result = cgProjectFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgProjectMapper::toVo));
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProductLibraryController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgProductLibraryVo>> save(Pageable pageable, ProductLibraryQueryCriteriaRequest criteriaRequest) {
        Page<ProductLibraryDto> list = cgProductLibraryFacade.list(pageable, criteriaRequest);
        return ResponseEntity.ok(list.map(productLibraryMapper::toVo));
    }

    @PostMapping("import")
    @Operation(summary = "导入")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProductLibraryController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> leading(@RequestParam("file")MultipartFile file, @Parameter(description = "采购目录ID")@RequestParam Integer purchaseCatalogId){
        InputStream inputStream=null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("导入失败");
        }
        List<CgProductLibraryImportDto> list=importTollProductLibrary.readExcel(inputStream);

        Integer size=cgProductLibraryFacade.saveList(list,purchaseCatalogId);
        return ResponseEntity.ok(list.size());
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    public ResponseEntity<byte[]> exportData(ProductLibraryQueryCriteriaRequest criteriaRequest){
        List<ProductLibraryDto> dto = cgProductLibraryFacade.getList(criteriaRequest);
        List<CgProductLibraryVo> list = dto.stream().map(productLibraryMapper::toVo).collect(Collectors.toList());
        return POIUtils.productLibrary2Excel(list);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.ProductLibraryController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@RequestParam Set<Integer> ids) {
        cgProductLibraryFacade.delete(ids);
        return ResponseEntity.ok().build();
    }


}
