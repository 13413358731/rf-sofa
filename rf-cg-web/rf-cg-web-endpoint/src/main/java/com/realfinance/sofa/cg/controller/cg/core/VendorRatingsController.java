package com.realfinance.sofa.cg.controller.cg.core;

import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgContractManageFacade;
import com.realfinance.sofa.cg.core.facade.CgParameterFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseResultNoticeFacade;
import com.realfinance.sofa.cg.core.facade.CgVendorRatingsFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgContractAttachmentVo;
import com.realfinance.sofa.cg.model.cg.CgContractManageVo;
import com.realfinance.sofa.cg.model.cg.CgVendorRatingsVo;
import com.realfinance.sofa.cg.service.mapstruct.CgContractManageMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgVendorRatingsMapper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.UserMngFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "供应商评分")
@RequestMapping("/cg/core/VendorRatingsController")
public class VendorRatingsController{
    private static final Logger log = LoggerFactory.getLogger(VendorRatingsController.class);

    public static final String MENU_CODE_ROOT = "vendorRating";

    @SofaReference(interfaceType = CgVendorRatingsFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgVendorRatingsFacade cgVendorRatingsFacade;

    @SofaReference(interfaceType = CgPurchaseResultNoticeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseResultNoticeFacade cgPurchaseResultNoticeFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @SofaReference(interfaceType = CgParameterFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgParameterFacade cgParameterFacade;

    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private UserMngFacade userMngFacade;

    @Resource
    private CgVendorRatingsMapper cgVendorRatingsMapper;

    @Resource
    private FileStore fileStore;

    @GetMapping("Vendorlist")
    @Operation(summary = "查询供应商评分列表")
//    @Parameters({
//            @Parameter(name = "startDateBefore", schema = @Schema(type = "string", format = "date-time"), description = "合同生效日期之前", in = ParameterIn.QUERY),
//            @Parameter(name = "startDateAfter", schema = @Schema(type = "string", format = "date-time"), description = "合同生效日期之后", in = ParameterIn.QUERY),
//            @Parameter(name = "expireDateBefore", schema = @Schema(type = "string", format = "date-time"), description = "合同到期之前", in = ParameterIn.QUERY),
//            @Parameter(name = "expireDateAfter", schema = @Schema(type = "string", format = "date-time"), description = "合同到期之后", in = ParameterIn.QUERY),
//    })
    public ResponseEntity<Page<CgVendorRatingsVo>> list(CgVendorRatingsQueryCriteria queryCriteria,
                                                        Pageable pageable) {
        Page<CgVendorRatingsDto> result = cgVendorRatingsFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgVendorRatingsMapper::toVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgVendorRatingsVo vo) {
        CgVendorRatingsSaveDto saveDto = cgVendorRatingsMapper.toSaveDto(vo);
        Integer id = cgVendorRatingsFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }


    @DeleteMapping("delete")
    @Operation(summary = "删除")
    public ResponseEntity<?> delete(@Parameter(description = "供应商评价ID") @RequestParam Set<Integer> id) {
        cgVendorRatingsFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
