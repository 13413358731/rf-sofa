package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgCommodityFacade;
import com.realfinance.sofa.cg.core.facade.CgParameterFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseResultNoticeFacade;
import com.realfinance.sofa.cg.core.facade.CgVendorRatingsFacade;
import com.realfinance.sofa.cg.core.model.CgCommodityDto;
import com.realfinance.sofa.cg.core.model.CgCommodityQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsDto;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsQueryCriteria;
import com.realfinance.sofa.cg.model.cg.CgCommodityVo;
import com.realfinance.sofa.cg.model.cg.CgVendorRatingsVo;
import com.realfinance.sofa.cg.service.mapstruct.CgCommodityMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgVendorRatingsMapper;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.UserMngFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@Tag(name = "采购商品")
@RequestMapping("/cg/core/CommodityController")
public class CommodityController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(CommodityController.class);

    public static final String MENU_CODE_ROOT = "commodity";

    @SofaReference(interfaceType = CgCommodityFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgCommodityFacade cgCommodityFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;


    @Resource
    private CgCommodityMapper cgCommodityMapper;

    @GetMapping("Commoditylist")
    @Operation(summary = "查询供应商评分列表")
//    @Parameters({
//            @Parameter(name = "startDateBefore", schema = @Schema(type = "string", format = "date-time"), description = "合同生效日期之前", in = ParameterIn.QUERY),
//            @Parameter(name = "startDateAfter", schema = @Schema(type = "string", format = "date-time"), description = "合同生效日期之后", in = ParameterIn.QUERY),
//            @Parameter(name = "expireDateBefore", schema = @Schema(type = "string", format = "date-time"), description = "合同到期之前", in = ParameterIn.QUERY),
//            @Parameter(name = "expireDateAfter", schema = @Schema(type = "string", format = "date-time"), description = "合同到期之后", in = ParameterIn.QUERY),
//    })
    public ResponseEntity<Page<CgCommodityVo>> list(CgCommodityQueryCriteria queryCriteria,
                                                    Pageable pageable) {
        Page<CgCommodityDto> result = cgCommodityFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgCommodityMapper::toVo));
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    public ResponseEntity<?> delete(@Parameter(description = "商品ID") @RequestParam Set<Integer> id) {
        cgCommodityFacade.delete(id);
        return ResponseEntity.ok().build();
    }


    @Override
    public String getBusinessCode() {
        return MENU_CODE_ROOT;
    }

    @Override
    public FlowFacade getFlowFacade() {
        return flowFacade;
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }

}
