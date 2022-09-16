package com.realfinance.sofa.cg.sup.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.sup.facade.CgBusinessProjectFacade;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectQueryCriteria;
import com.realfinance.sofa.cg.sup.security.SupplierUser;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "商务项目")
@RequestMapping("/cg/sup/bizproj")
public class BusinessProjectController {

    private static final Logger log = LoggerFactory.getLogger(BusinessProjectController.class);

    @SofaReference(interfaceType = CgBusinessProjectFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessProjectFacade cgBusinessProjectFacade;

    @GetMapping("list")
    @Operation(summary = "查询")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Page<CgBusinessProjectDto>> list(@ParameterObject CgBusinessProjectQueryCriteria queryCriteria,
                                                           Pageable pageable,
                                                           @AuthenticationPrincipal SupplierUser supplierUser) {
        Integer id = supplierUser.getSupplierId();
        queryCriteria.setSupplierId(id);
        Page<CgBusinessProjectDto> result = cgBusinessProjectFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result);
    }

}
